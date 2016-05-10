package co.edu.icesi.eketal.outputconfiguration

import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import com.google.inject.Inject
import co.edu.icesi.eketal.generator.EketalGenerator

//TODO
//para el warnning http://help.eclipse.org/juno/index.jsp?topic=/org.eclipse.pde.doc.user/guide/tools/editors/manifest_editor/access_rules.htm
//http://stackoverflow.com/questions/1089904/access-restriction-on-class-due-to-restriction-on-required-library
class OutputConfigurationAwaredGenerator extends JvmModelGenerator{
	
	@Inject private EketalGenerator generator
	
	/*
	 * More information on using both IGenerator and IJvmModelInferrer:
	 * https://www.eclipse.org/forums/index.php/t/486215/
	 */
	override void doGenerate(Resource input, IFileSystemAccess fsa) {
		val _contents = input.getContents()
		for (obj : _contents) {
			val adapters = obj.eAdapters.filter(OutputConfigurationAdapter)
			for (adapter : adapters) {
				var outputConfiguration = adapter.getOutputConfigurationName()
				println(outputConfiguration.toString)
				if (outputConfiguration == EketalOutputConfigurationProvider::EKETAL_OUTPUT) {
					val sfsa = new SingleOutputConfigurationFileSystemAccess(fsa, outputConfiguration)
					this.internalDoGenerate(obj, sfsa) // PascaniJvmModelInferrer
				} else if (outputConfiguration == EketalOutputConfigurationProvider::ASPECTJ_OUTPUT) {
					this.generator.doGenerate(input, fsa)
				}
			}
			if (adapters.isEmpty) {
				this.internalDoGenerate(obj, fsa)
			}
		}
	}
	
}