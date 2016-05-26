package co.edu.icesi.eketal.generator

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import co.edu.icesi.eketal.eketal.EvDecl
import co.edu.icesi.eketal.eketal.EventClass
import co.edu.icesi.eketal.eketal.AndEvent
import co.edu.icesi.eketal.eketal.EventExpression
import co.edu.icesi.eketal.eketal.OrEvent
import co.edu.icesi.eketal.eketal.Trigger
import co.edu.icesi.eketal.eketal.KindAttribute
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider
import java.io.File
import co.edu.icesi.eketal.eketal.UnaryEvent
import co.edu.icesi.eketal.eketal.JVarD
import java.util.Set
import java.util.TreeSet
import java.util.ArrayList
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer
import org.eclipse.xtext.xbase.impl.XStringLiteralImpl
import org.eclipse.xtext.xbase.XStringLiteral

//https://www.eclipse.org/forums/index.php/t/486215/

class EketalGenerator implements IGenerator{
		
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		println("doGenerate")
		
		resource.allContents.filter(typeof(EventClass)).forEach[it.generateAspect(fsa)]
	}
	
	def generateAspect(EventClass modelo, IFileSystemAccess fsa){
		var packageName = "co.edu.icesi.eketal.aspects"
//		fsa.generateFile(prepareFileName("./"+packageName, modelo.name.toFirstUpper), EketalOutputConfigurationProvider::ASPECTJ_OUTPUT, modelo.generate(packageName))
		fsa.generateFile(prepareFileName("./"+packageName, modelo.name.toFirstUpper), IFileSystemAccess.DEFAULT_OUTPUT, modelo.generate(packageName))
	}
	
	
	def prepareFileName(String packageName, String fileName) {
		return (packageName + "." + fileName).replaceAll("\\.", File.separator) + ".aj"
	}
	
	def CharSequence generate(EventClass modelo, String packageName){
		var paquete = '''package «packageName»;
		
		'''
		var Set<String> importaciones = new TreeSet()
		var pointcuts = new ArrayList<String>
		importaciones+="co.edu.icesi.eketal.automaton.*"
		importaciones+="co.edu.icesi.eketal.groupsimpl.*"
		var aspect = '''
		public aspect «modelo.name.toFirstUpper»{
		
			//private Automaton automata = miprieraclase.getAutomaton();
			
			«FOR event:modelo.declarations»
				«IF event instanceof JVarD»
					//«importaciones+=agregarImports((event as JVarD).type.qualifiedName)»
					//--------Evento: «event.name.toString»-------------
					private «(event as JVarD).type.simpleName» «(event as JVarD).name.toFirstLower»;
				«ENDIF»
				«IF event instanceof EvDecl»
					//--------Evento: «event.name.toString»-------------
					pointcut «event.name.toFirstLower»():
						«createPointCut(event as EvDecl, pointcuts)»;
						
					after() returning (Object o): «event.name.toFirstLower»() {
						System.out.println("Returned normally with " + o);
					}
					after() throwing (Exception e): «event.name.toFirstLower»() {
						System.out.println("Threw an exception: " + e);
					}
					after(): «event.name.toFirstLower»(){
						System.out.println("Returned or threw an Exception");
					}
				«ENDIF»
			«ENDFOR»
			
			«FOR pointcut:pointcuts»
				«pointcut»;
			«ENDFOR»
			
		}
		'''
		var imports = '''
		«FOR tipo:importaciones»
			import «tipo»;
		«ENDFOR»
		
		'''
		return paquete+imports+aspect
	}
	
	def agregarImports(String name) {
		var lista = new ArrayList
		if(!name.contains('<')){
			lista.add(name)			
		}else{
			var String[] strings = name.split("<")
			for(string:strings){
				lista.add(string.replaceAll(">",""))
			}
		}
		return lista
	}
	
	def createPointCut(EvDecl decl, ArrayList<String> pointcuts) {
		var ArrayList<String> eventos = new ArrayList
		for(event : decl.eventos){
			eventos+=eventExpression(event as EventExpression, pointcuts)
		}
		val String valor = eventos.toString.substring(1, eventos.toString.length-1)
		return valor
	}
	
	def eventExpression(EventExpression event, ArrayList<String> pointcuts) {
			if(event.tipoEvento!=null){
				var tipoEvento = event.tipoEvento
				switch(tipoEvento){
					Trigger:{
						var pointcutTemp = returnCall(tipoEvento as Trigger)
//						println("---porintcut: "+pointcutTemp.get(0)+"---"+pointcutTemp.get(1))
						pointcuts+=pointcutTemp.get(1).toString
						return pointcutTemp.get(0).toString
					}
					KindAttribute:{
						return returnAttribute(tipoEvento as KindAttribute)						
					}
				}
			}else{
				
				switch(event){
					AndEvent:{
						//AndEvent
						var andEvent = event as AndEvent
						return 
						'''(«eventExpression(andEvent.left as EventExpression, pointcuts)» && «eventExpression(andEvent.right as EventExpression, pointcuts)»)'''
					}
					OrEvent: {
						//OrEvent
						var orEvent = event as OrEvent
						return 
						'''(«eventExpression(orEvent.left as EventExpression, pointcuts)» || «eventExpression(orEvent.right as EventExpression, pointcuts)»)'''
					}
					UnaryEvent:{
						var unaryEvent = event as UnaryEvent
						return
						'''!«eventExpression(unaryEvent.expr as EventExpression, pointcuts)»'''
					}
				}
			}
	}
	
	def returnAttribute(KindAttribute attribute) {
		if(attribute.condition!=null){
			println(attribute.condition.eContents.size)
			var body = ""
			if(attribute.condition.eContents.size==1){
				var XStringLiteralImpl valone = attribute.condition.eContents.get(0) as XStringLiteralImpl
				body = '''«valone.value»'''
			}else{
				if(attribute.condition.eContents.get(0) instanceof XStringLiteralImpl && attribute.condition.eContents.get(1) instanceof XStringLiteralImpl){
					var XStringLiteralImpl valone = attribute.condition.eContents.get(0) as XStringLiteralImpl
					var XStringLiteralImpl valtwo = attribute.condition.eContents.get(1) as XStringLiteralImpl
					body = '''"«valone.value»".equals("«valtwo.value»")'''
				}
			}
			return '''if(«body»)'''
		}else if(attribute.hostgroup!=null){
			return '''«EketalJvmModelInferrer.groupClassName».host(«attribute.hostgroup.name»)'''
		}else if(attribute.ongroup!=null){
			return '''if(«EketalJvmModelInferrer.groupClassName».on("«attribute.ongroup.name»"))'''
//			return '''on()'''//TODO acá debe hacer otro procesamiento dado que este elemento no está
//			//soportado por aspectj
		}
	}
	
	def returnCall(Trigger trigger) {
		//la primera posición es el nombre del pointcut, la segunda es la definición
		var CharSequence[] retorno = newArrayList('''point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»()''', '''pointcut point«trigger.esig.toString.replaceAll("\\.", "").toFirstUpper»(): call(* «trigger.esig»(«trigger.params.join(',')»))''')
		return retorno
	}
	
}