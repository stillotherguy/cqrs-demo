package com.example.cqrs.domain

import java.util.*

data class FindFoodCartQuery(val foodCartId: UUID)

class RetrieveProductOptionsQuery