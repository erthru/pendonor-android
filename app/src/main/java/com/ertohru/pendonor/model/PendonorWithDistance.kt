package com.ertohru.pendonor.model

data class PendonorWithDistance (
    val id:String,
    val namaLengkap:String,
    val alamat:String,
    val telp:String,
    val jenkel:String,
    val golonganDarah:String,
    val resus:String,
    val umur:String,
    val pekerjaan:String,
    val lat:String,
    val lng:String,
    val createdAt:String,
    val updatedAt:String,
    val distance: String
)