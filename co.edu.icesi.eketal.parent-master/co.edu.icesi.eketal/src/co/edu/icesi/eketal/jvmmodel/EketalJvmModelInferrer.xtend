/*
 * generated by Xtext 2.9.2
 */
package co.edu.icesi.eketal.jvmmodel

import co.edu.icesi.eketal.eketal.Model
import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import java.util.TreeMap
import java.util.Set
import org.eclipse.xtext.common.types.JvmVisibility
import co.edu.icesi.eketal.eketal.Group
import org.eclipse.xtext.common.types.JvmField
import java.util.List
import java.util.ArrayList
import java.util.HashSet
import co.edu.icesi.eketal.eketal.StateType
import co.edu.icesi.ketal.core.State
import co.edu.icesi.ketal.core.Event
import java.util.Map
import java.util.HashMap
import co.edu.icesi.ketal.core.Automaton
import co.edu.icesi.ketal.core.Transition
import co.edu.icesi.ketal.core.DefaultEqualsExpression
import java.util.TreeSet
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.icesi.eketal.outputconfiguration.OutputConfigurationAdapter
import co.edu.icesi.eketal.outputconfiguration.EketalOutputConfigurationProvider
import co.edu.icesi.ketal.core.NamedEvent

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class EketalJvmModelInferrer extends AbstractModelInferrer {

    /**
     * convenience API to build and initialize JVM types and their members.
     */
	@Inject extension JvmTypesBuilder

	@Inject extension IQualifiedNameProvider
	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
	def dispatch void infer(Model element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		// Here you explain how your model is mapped to Java elements, by writing the actual translation code.
		println("Inferring model for " + element.name)
		
		val implementacion = element.toClass(element.fullyQualifiedName)
		
		if(implementacion==null)
			return;
		
		var eventClass = element.typeDeclaration
		var eventClassImpl = eventClass.toClass(eventClass.fullyQualifiedName)
		
		eventClassImpl.eAdapters.add(new OutputConfigurationAdapter(EketalOutputConfigurationProvider::ASPECTJ_OUTPUT))
		eventClassImpl.eAdapters.add(new OutputConfigurationAdapter(EketalOutputConfigurationProvider::EKETAL_OUTPUT))

		acceptor.accept(eventClassImpl).initializeLater[
			println("dentra")
		]
		
		acceptor.accept(element.toClass("co.edu.icesi.eketal.automaton."+element.name.toFirstUpper)) [
			println("co.edu.icesi.ketal.automaton."+element.name)
			var declaraciones = element.typeDeclaration.declarations
			for (declaracion : declaraciones) {
				switch(declaracion){
					co.edu.icesi.eketal.eketal.Automaton:{
						members+=declaracion.toField(declaracion.name, typeRef(Automaton))
						members+=declaracion.toConstructor[
							body = '''
							inicialize();
							'''
						]
						members += AutomatonInit(declaracion)
					}
					Group:{
						
					}
				}
			}
		]
		
//		acceptor.accept(element.toClass("co.edu.icesi.ketal.automaton."+element.name)) [
//			members += element.toConstructor[
//				body = '''System.out.println("Hello world");'''
//			]
//		]
		
	}
	
	def AutomatonInit(co.edu.icesi.eketal.eketal.Automaton declaracion) {
		val method = declaracion.toMethod("inicialize", typeRef(void))[
		visibility = JvmVisibility::PRIVATE
		body = '''
		//Relación evento caracter
		«typeRef(Map)»<String, Character> mapping = new «typeRef(TreeMap)»<String, Character>();
		//Estado inicial
		«typeRef(State)» inicial = null;
		//lista de estados finales
		«typeRef(Set)»<«typeRef(State)»> estadosFinales = new «typeRef(HashSet)»();
		//Conjunto de nombres y estados
		«typeRef(Map)»<String, «typeRef(State)»> estados = new «typeRef(HashMap)»();
		
		//map de eventos con transiciones
		«typeRef(Map)»<«typeRef(DefaultEqualsExpression)», «typeRef(Transition)»> eventos = new «typeRef(HashMap)»();
		
		int consecutivo = 0;
		Character caracter = (char)consecutivo;
		String nombreEvento = "";
		String estadoLlegada = "";
		
		«FOR step : declaracion.steps»
			//Definición del estado: «step.name»
			String estado«step.name.toFirstUpper» = "«step.name»";
			estados.put(estado«step.name.toFirstUpper», new «typeRef(State)»());
			«IF step.type!=null && step.type==StateType.START»
				//«step.type» «StateType.START» «StateType.START_VALUE»
				//Estado inicial: «step.name»
				inicial = estados.get(estado«step.name.toFirstUpper»);
			«ENDIF»
			
			«IF !step.transitions.isEmpty && step.type!=StateType.END»
				«FOR transition : step.transitions»
				//"Transiciones de " + «transition.event.name»+" -> "+«transition.target.name»
					estadoLlegada = "«transition.target.name»";
					if(!estados.containsKey(estadoLlegada)){
						estados.put(estado«step.name.toFirstUpper», new «typeRef(State)»());
					}
					caracter = (char)consecutivo;
					consecutivo++;
					nombreEvento = "«transition.event.name»";
					mapping.put(nombreEvento, caracter);
					co.edu.icesi.ketal.core.Transition «step.name»«transition.event.name.toFirstUpper» = new «typeRef(Transition)»(estados.get(estado«step.name.toFirstUpper»), estados.get(estadoLlegada), caracter);
					eventos.put(new «typeRef(DefaultEqualsExpression)»(new «typeRef(NamedEvent)»(nombreEvento)), «step.name»«transition.event.name.toFirstUpper»);
				«ENDFOR»
			«ELSE»
				//Estado final «step.name.toFirstUpper»
				estadosFinales.add(estados.get(estado«step.name.toFirstUpper»));
			«ENDIF»
		«ENDFOR»
		«TreeSet.canonicalName» transitionSet = new «TreeSet.canonicalName»();
		transitionSet.addAll(eventos.values());
		«typeRef(Automaton)» automata = new Automaton(transitionSet, inicial, estadosFinales);
		automata.initializeAutomaton();
		«declaracion.name» = automata;
		'''
		]
		return method
	}
	
//				members += automaton.toMethod("transiciones", typeRef(Set))[
//					abstract = true
//					static = true
//					visibility = JvmVisibility.PRIVATE
//					body = '''
//					return null;
//	   				'''
//				]
	
}