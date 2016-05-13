package co.edu.icesi.eketal.generator

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import co.edu.icesi.eketal.eketal.EvDecl
import co.edu.icesi.eketal.eketal.Model
import co.edu.icesi.eketal.eketal.EventClass
import co.edu.icesi.eketal.eketal.Decl
import co.edu.icesi.eketal.eketal.AndEvent
import co.edu.icesi.eketal.eketal.EventExpression
import co.edu.icesi.eketal.eketal.OrEvent
import co.edu.icesi.eketal.eketal.Trigger
import co.edu.icesi.eketal.eketal.KindAttribute
import co.edu.icesi.eketal.eketal.EventPredicate
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider
import java.io.File
import java.util.List
import co.edu.icesi.eketal.eketal.UnaryEvent
import co.edu.icesi.eketal.eketal.JVarD
import java.util.Set
import java.util.TreeSet
import org.eclipse.xtext.common.types.JvmTypeReference
import java.util.ArrayList

//https://www.eclipse.org/forums/index.php/t/486215/

class EketalGenerator implements IGenerator{
		
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		println("doGenerate")
		
		resource.allContents.filter(typeof(EventClass)).forEach[it.generateAspect(fsa)]
	}
	
	def generateAspect(EventClass modelo, IFileSystemAccess fsa){
		var packageName = "co/edu/icesi/eketal/aspects"
//		var ruta = EketalOutputConfigurationProvider::ASPECTJ_OUTPUT+"-gen/"+packageName+"."+modelo.name.toFirstUpper+".aj"
//		println("Ruta ="+ruta)
		
//		fsa.generateFile(EketalOutputConfigurationProvider::ASPECTJ_OUTPUT+"/"+modelo.name.toFirstUpper+".aj", modelo.generate(packageName))
		fsa.generateFile("./"+packageName+"/"+modelo.name.toFirstUpper+".aj", modelo.generate(packageName.replaceAll("/",".")))
	}
	
	
	//TODO usar metodo
	def prepareFileName(String fileName, String packageName) {
		return (packageName + "." + fileName).replaceAll("\\.", File.separator) + ".aj"
	}
	
	def CharSequence generate(EventClass modelo, String packageName){
		var paquete = '''package «packageName»;
		
		'''
		var Set<String> importaciones = new TreeSet()
		importaciones+="co.edu.icesi.eketal.automaton"
		var aspect = '''
		public aspect «modelo.name.toFirstUpper»{
			
			//private Automaton = new Automaton
			
			«FOR event:modelo.declarations»
				«IF event instanceof JVarD»
					//«importaciones+=agregarImports((event as JVarD).type.qualifiedName)»
					//--------Evento: «println(event.name.toString)»-------------
					private «(event as JVarD).type.simpleName» «(event as JVarD).name.toFirstLower»;
				«ENDIF»
				«IF event instanceof EvDecl»
					//--------Evento: «println(event.name.toString)»-------------
					pointcut «event.name.toFirstLower»:
						«createPointCut(event as EvDecl)»;
					after() returning (Object o): «event.name.toFirstLower» {
						System.out.println("Returned normally with " + o);
					}
					after() throwing (Exception e): «event.name.toFirstLower» {
						System.out.println("Threw an exception: " + e);
					}
					after(): «event.name.toFirstLower»{
						System.out.println("Returned or threw an Exception");
					}
				«ENDIF»
				
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
	
	def createPointCut(EvDecl decl) {
		println("----------------------")
		var ArrayList<String> eventos = new ArrayList
		for(event : decl.eventos){
			eventos+=eventExpression(event as EventExpression)
		}
		return eventos
	}
	
	def eventExpression(EventExpression event) {
			if(event.tipoEvento!=null){
				var tipoEvento = event.tipoEvento
				switch(tipoEvento){
					Trigger:{
						return returnCall(tipoEvento as Trigger)
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
						'''«eventExpression(andEvent.left as EventExpression)»&&«eventExpression(andEvent.right as EventExpression)»'''
					}
					OrEvent: {
						//OrEvent
						var orEvent = event as OrEvent
						return 
						'''«eventExpression(orEvent.left as EventExpression)»||«eventExpression(orEvent.right as EventExpression)»'''
					}
					UnaryEvent:{
						var unaryEvent = event as UnaryEvent
						return
						'''!«eventExpression(unaryEvent.expr as EventExpression)»'''
					}
				}
			}
	}
	
	def returnAttribute(KindAttribute attribute) {
		println("KindEvent")
		if(attribute.condition!=null){
			return '''if()'''//TODO terminar
		}else if(attribute.hostgroup!=null){
			return '''host(«attribute.hostgroup.name»)'''
		}else if(attribute.ongroup!=null){
			return '''on()'''//TODO acá debe hacer otro procesamiento dado que este elemento no está
			//soportado por aspectj
		}
	}
	
	def returnCall(Trigger trigger) {
		println("Trigger")
		return '''call * «trigger.esig»(«trigger.params.join(',')»)'''
	}
	
}