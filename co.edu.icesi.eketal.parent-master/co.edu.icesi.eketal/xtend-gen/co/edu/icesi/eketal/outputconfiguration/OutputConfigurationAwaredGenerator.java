package co.edu.icesi.eketal.outputconfiguration;

import co.edu.icesi.eketal.generator.EketalGenerator;
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider;
import co.edu.icesi.eketal.outputconfiguration.OutputConfigurationAdapter;
import co.edu.icesi.eketal.outputconfiguration.SingleOutputConfigurationFileSystemAccess;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class OutputConfigurationAwaredGenerator extends JvmModelGenerator {
  @Inject
  private EketalGenerator generator;
  
  /**
   * More information on using both IGenerator and IJvmModelInferrer:
   * https://www.eclipse.org/forums/index.php/t/486215/
   */
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    final EList<EObject> _contents = input.getContents();
    for (final EObject obj : _contents) {
      {
        EList<Adapter> _eAdapters = obj.eAdapters();
        final Iterable<OutputConfigurationAdapter> adapters = Iterables.<OutputConfigurationAdapter>filter(_eAdapters, OutputConfigurationAdapter.class);
        for (final OutputConfigurationAdapter adapter : adapters) {
          {
            String outputConfiguration = adapter.getOutputConfigurationName();
            boolean _equals = Objects.equal(outputConfiguration, EketalOutputConfigurationProvider.EKETAL_OUTPUT);
            if (_equals) {
              final SingleOutputConfigurationFileSystemAccess sfsa = new SingleOutputConfigurationFileSystemAccess(fsa, outputConfiguration);
              this.internalDoGenerate(obj, sfsa);
            } else {
              boolean _or = false;
              boolean _equals_1 = Objects.equal(outputConfiguration, EketalOutputConfigurationProvider.ASPECTJ_OUTPUT);
              if (_equals_1) {
                _or = true;
              } else {
                boolean _equals_2 = Objects.equal(IFileSystemAccess.DEFAULT_OUTPUT, outputConfiguration);
                _or = _equals_2;
              }
              if (_or) {
                this.generator.doGenerate(input, fsa);
              }
            }
          }
        }
        boolean _isEmpty = IterableExtensions.isEmpty(adapters);
        if (_isEmpty) {
          this.internalDoGenerate(obj, fsa);
        }
      }
    }
  }
}
