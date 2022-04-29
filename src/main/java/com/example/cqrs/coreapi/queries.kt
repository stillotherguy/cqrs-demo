package com.example.cqrs.coreapi

import java.util.*

data class FindFoodCartQuery(val foodCartId: UUID)

class RetrieveProductOptionsQuery