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

//https://www.eclipse.org/forums/index.php/t/486215/

class EketalGenerator implements IGenerator{
	
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		println("doGenerate")
		
		resource.allContents.filter(typeof(EventClass)).forEach[it.generateAspect(fsa)]
	}
	
	def generateAspect(EventClass modelo, IFileSystemAccess fsa){
		fsa.generateFile(EketalOutputConfigurationProvider::ASPECTJ_OUTPUT+modelo.name.toFirstUpper+".aj", modelo.generate)
	}
	
	//TODO usar metodo
	def prepareFileName(String fileName, String packageName) {
		return (packageName + "." + fileName).replaceAll("\\.", File.separator) + ".aj"
	}
	
	def CharSequence generate(EventClass modelo){
		return '''
		package «EketalOutputConfigurationProvider::ASPECTJ_OUTPUT»
		public aspect «modelo.name.toFirstUpper»{
			«FOR event:modelo.declarations»
				«IF event.class==EvDecl»
					«createPointCut(event as EvDecl)»
				«ENDIF»
			«ENDFOR»
		}
		'''
		
	}
	
	def createPointCut(EvDecl decl) {
		for(event : decl.eventos){
			switch(event as EventExpression){
				OrEvent: return ""
				AndEvent: return ""
				Trigger: return ""
				KindAttribute: return ""
//				UnaryExpresion: return ""
			}
		}
	}
	
	def eventPredicate(EventPredicate ep){
		switch(ep){
			Trigger:
				return returnCall(ep as Trigger)
			KindAttribute:
				return returnAttribute(ep as KindAttribute)
		}
	}
	
	def returnAttribute(KindAttribute attribute) {
		if(attribute.condition!=null){
			//TODO este es una expresión booleana
		}else if(attribute.hostgroup!=null){
			return '''host(«attribute.hostgroup.name»)'''
		}else if(attribute.ongroup!=null){
			//TODO acá debe hacer otro procesamiento dado que este elemento no está
			//soportado por aspectj
		}
	}
	
	def returnCall(Trigger trigger) {
		return '''call «trigger.esig»(«trigger.params.join(',')»);
		after() returning (Object o): «"nombreMetodo()"» {
		System.out.println("Returned normally with " + o);
		}
		after() throwing (Exception e): «"nombreMetodo()"» {
		System.out.println("Threw an exception: " + e);
		}
		after(): «"nombreMetodo()"»{
		System.out.println("Returned or threw an Exception");
		}'''
	}
	
}