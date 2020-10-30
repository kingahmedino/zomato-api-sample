package com.app.zomatoapisample.models

class Restaurant(var id: String,
                 var name: String,
                 var address: String,
                 var city: String,
                 var image: String?,
                 var longitude: Double,
                 var latitude: Double,
                 var locality: String) {
}