/**
 * generated by Xtext 2.9.2
 */
package co.edu.icesi.eketal.eketal.impl;

import co.edu.icesi.eketal.eketal.EketalPackage;
import co.edu.icesi.eketal.eketal.EvDecl;
import co.edu.icesi.eketal.eketal.Step;
import co.edu.icesi.eketal.eketal.TransDef;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trans Def</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link co.edu.icesi.eketal.eketal.impl.TransDefImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link co.edu.icesi.eketal.eketal.impl.TransDefImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransDefImpl extends MinimalEObjectImpl.Container implements TransDef
{
  /**
   * The cached value of the '{@link #getEvent() <em>Event</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEvent()
   * @generated
   * @ordered
   */
  protected EvDecl event;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected Step target;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransDefImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EketalPackage.Literals.TRANS_DEF;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EvDecl getEvent()
  {
    if (event != null && event.eIsProxy())
    {
      InternalEObject oldEvent = (InternalEObject)event;
      event = (EvDecl)eResolveProxy(oldEvent);
      if (event != oldEvent)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, EketalPackage.TRANS_DEF__EVENT, oldEvent, event));
      }
    }
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EvDecl basicGetEvent()
  {
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEvent(EvDecl newEvent)
  {
    EvDecl oldEvent = event;
    event = newEvent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EketalPackage.TRANS_DEF__EVENT, oldEvent, event));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Step getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (Step)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, EketalPackage.TRANS_DEF__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Step basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(Step newTarget)
  {
    Step oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EketalPackage.TRANS_DEF__TARGET, oldTarget, target));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EketalPackage.TRANS_DEF__EVENT:
        if (resolve) return getEvent();
        return basicGetEvent();
      case EketalPackage.TRANS_DEF__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EketalPackage.TRANS_DEF__EVENT:
        setEvent((EvDecl)newValue);
        return;
      case EketalPackage.TRANS_DEF__TARGET:
        setTarget((Step)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EketalPackage.TRANS_DEF__EVENT:
        setEvent((EvDecl)null);
        return;
      case EketalPackage.TRANS_DEF__TARGET:
        setTarget((Step)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EketalPackage.TRANS_DEF__EVENT:
        return event != null;
      case EketalPackage.TRANS_DEF__TARGET:
        return target != null;
    }
    return super.eIsSet(featureID);
  }

} //TransDefImpl
