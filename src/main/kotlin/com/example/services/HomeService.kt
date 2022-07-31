package com.example.services

import jakarta.inject.Singleton

@Singleton
class HomeService {
    fun giveFive(): String {
        return "Five"
    }
}