/*
 * generated by Xtext 2.9.2
 */
package co.edu.icesi.eketal


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class EketalStandaloneSetup extends EketalStandaloneSetupGenerated {

	def static void doSetup() {
		new EketalStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}