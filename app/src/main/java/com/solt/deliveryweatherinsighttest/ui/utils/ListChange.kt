package com.solt.deliveryweatherinsighttest.ui.utils

data class  ListChange<T> (
    val addedItems :List<T>,
    val removedItems:List<T>,
    val modifiedItems :List<Pair<T,T>>

)

object ListChangeCalculator{
    fun <T> getDifferenceBetweenCurrentListAndNewList(currentList:List<T>,newList:List<T>):ListChange<T>{
        val addedItems = newList - currentList.toSet()
        val removedItems = currentList-newList.toSet()
        val modifiedItems =
            currentList.zip(newList).filter {(olditem,newitem)->
               olditem!=newitem
            }

        return ListChange(addedItems,removedItems,modifiedItems)


    }
}
