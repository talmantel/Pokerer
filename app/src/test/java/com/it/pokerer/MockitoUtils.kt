package com.it.pokerer

import org.mockito.MockSettings
import org.mockito.Mockito

inline fun <reified T> mock(settings: MockSettings = Mockito.withSettings()): T = Mockito.mock(T::class.java, settings)
