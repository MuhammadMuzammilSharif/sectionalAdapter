package io.github.muhammadmuzammilsharif.interfaces;

/*
 * Created by M_Muzammil Sharif on 10-Apr-18.
 */
interface SectionalUniqueObject<K> {
    fun getUniqueKey(): Any

    fun getSection(): K
}
