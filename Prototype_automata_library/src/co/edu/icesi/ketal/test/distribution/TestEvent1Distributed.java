package co.edu.icesi.ketal.test.distribution;

import java.io.Serializable;
import java.net.URL;

import co.edu.icesi.ketal.core.Event;
public class TestEvent1Distributed  implements Event, Serializable{

		public Character alphabet;
		
		public TestEvent1Distributed(Character a) {
			alphabet=a;

		}
		
		public Character getCharacterOfAlphabet() {
			// TODO Auto-generated method stub
			return alphabet;
		}


		@Override
		public boolean equals(Event anEvent) {
			if (this == anEvent)
				return true;
			if (anEvent == null)
				return false;
			if (getClass() != anEvent.getClass())
				return false;
			TestEvent1Distributed other = (TestEvent1Distributed) anEvent;
			if (alphabet == null) {
				if (other.alphabet != null)
					return false;
			} else if (!alphabet.equals(other.alphabet))
				return false;
			return true;
		}
	
		@Override
		public String toString()
		{
		return alphabet+"";	
		}

		@Override
		public URL getLocalization() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLocalization(URL url) {
			// TODO Auto-generated method stub
			
		}
}