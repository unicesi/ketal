package co.edu.icesi.ketal.core;

import java.net.URL;

/**
 * It describes any class with Event Behavior. It means, the programmer can
 * adapt its classes, to be used in this library by implementing this interface
 */
public interface Event
{
	/**
	 * Method to determine if two events are equal
	 * @param e Event to compare
	 * @return boolean true if the events are equal, false in any other case
	 */
	public boolean equals(Event e);

	/**
	 * Method to get the localization of the event in form of a class URL
	 * @return URL
	 */
	public URL getLocalization();
	
	/**
	 * Method to set the localization of the event
	 * @param url Representing the localization
	 */
	public void setLocalization(URL url);
	
	
	//todo: documentar
		//public Character getCharacterOfAlphabet();
		
		//todo: documentar (determinar si el caracter es el que está asociado con el evento).
		//todo: modificar lo necesario, se cambió el nombre incluyendo el "is" y se adicionó el parámetro.
		//public boolean isCharacterOfAlphabet(Character myChar);
		
		//todo: documentar
		//todo: eliminar este método
		//public int hashCode();
}
