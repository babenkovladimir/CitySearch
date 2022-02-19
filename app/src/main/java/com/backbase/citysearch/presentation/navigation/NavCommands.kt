package com.backbase.citysearch.presentation.navigation

import com.backbase.citysearch.domain.entity.City

class MapNavigationCommand(val city: City) : NavCommand()

object SearchNavigationCommand : NavCommand()