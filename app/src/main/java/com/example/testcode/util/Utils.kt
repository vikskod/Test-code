package com.example.testcode.util

import androidx.fragment.app.Fragment
import com.example.testcode.data.network.Resource

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    //TODO handle API error here
}