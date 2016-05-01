/**
 * generated by Xtext 2.9.2
 */
package co.edu.icesi.eketal.eketal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xtext.common.types.JvmFormalParameter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link co.edu.icesi.eketal.eketal.Trigger#getEsig <em>Esig</em>}</li>
 *   <li>{@link co.edu.icesi.eketal.eketal.Trigger#getParams <em>Params</em>}</li>
 * </ul>
 *
 * @see co.edu.icesi.eketal.eketal.EketalPackage#getTrigger()
 * @model
 * @generated
 */
public interface Trigger extends EventPredicate
{
  /**
   * Returns the value of the '<em><b>Esig</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Esig</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Esig</em>' attribute.
   * @see #setEsig(String)
   * @see co.edu.icesi.eketal.eketal.EketalPackage#getTrigger_Esig()
   * @model
   * @generated
   */
  String getEsig();

  /**
   * Sets the value of the '{@link co.edu.icesi.eketal.eketal.Trigger#getEsig <em>Esig</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Esig</em>' attribute.
   * @see #getEsig()
   * @generated
   */
  void setEsig(String value);

  /**
   * Returns the value of the '<em><b>Params</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.xtext.common.types.JvmFormalParameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Params</em>' containment reference list.
   * @see co.edu.icesi.eketal.eketal.EketalPackage#getTrigger_Params()
   * @model containment="true"
   * @generated
   */
  EList<JvmFormalParameter> getParams();

} // Trigger
