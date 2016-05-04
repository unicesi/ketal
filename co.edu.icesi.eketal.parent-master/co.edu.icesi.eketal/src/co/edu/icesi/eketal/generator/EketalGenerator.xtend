package co.edu.icesi.eketal.generator

import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import co.edu.icesi.eketal.eketal.EvDecl

class EketalGenerator implements IGenerator{
	
	override doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		println("doGenerate")
		
		resource.allContents.filter(typeof(EvDecl)).forEach[it.generateAspect(fsa)]
		
	}
	
	def void generateAspect(EvDecl decl, IFileSystemAccess access){
		
	}
	
}