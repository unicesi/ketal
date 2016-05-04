package co.edu.icesi.eketal.generator;

import co.edu.icesi.eketal.eketal.EvDecl;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class EketalGenerator implements IGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    InputOutput.<String>println("doGenerate");
    TreeIterator<EObject> _allContents = resource.getAllContents();
    Iterator<EvDecl> _filter = Iterators.<EvDecl>filter(_allContents, EvDecl.class);
    final Procedure1<EvDecl> _function = (EvDecl it) -> {
      this.generateAspect(it, fsa);
    };
    IteratorExtensions.<EvDecl>forEach(_filter, _function);
  }
  
  public void generateAspect(final EvDecl decl, final IFileSystemAccess access) {
  }
}
