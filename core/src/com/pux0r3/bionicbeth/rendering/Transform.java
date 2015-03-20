package com.pux0r3.bionicbeth.rendering;

/**
 * Created by Patrick on 2/15/2015.
 */
public class Transform {
    private Transform m_parent;

    public void setParent(Transform parent) {
        this.m_parent = parent;
    }

    public Transform getM_parent() {
        return m_parent;
    }

    public void setM_parent(Transform m_parent) {
        this.m_parent = m_parent;
    }

    public Transform getParent() {
        return m_parent;
    }
}
