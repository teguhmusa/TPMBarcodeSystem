package com.teguhmusaharpa.tpmbarcodesystem;

/**
 * Created by Teguh Musaharpa on 10/18/2017.
 */

public class config {

    public static final String URL_ADD="http://192.168.43.28/tpm/tambahData.php";
    public static final String URL_GET_ALL = "http://192.168.43.28/tpm/tampilSemua.php";
    public static final String URL_GET_EMP = "http://192.168.43.28/tpm/tampilPegawai.php?nik=";
    public static final String URL_UPDATE_EMP = "http://192.168.43.28/tpm/updateLokasi.php";
    public static final String URL_DELETE_EMP = "http://192.168.43.28/tpm/deleteData.php?nik=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_NIK = "nik";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "status"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_GAJIH = "level"; //salary itu variabel untuk gajih

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_NIK = "nik";
    public static final String TAG_NAMA = "name";
    public static final String TAG_STATUS = "status";
    public static final String TAG_LEVEL = "level";


    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
