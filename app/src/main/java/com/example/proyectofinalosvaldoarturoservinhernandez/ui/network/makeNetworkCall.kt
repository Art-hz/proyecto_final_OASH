package com.example.proyectofinalosvaldoarturoservinhernandez.ui.network

import android.util.Log
import com.example.proyectofinalosvaldoarturoservinhernandez.R
import com.example.proyectofinalosvaldoarturoservinhernandez.ui.network.model.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponseStatus.Error(R.string.unknown_host_exception_error)
    } catch (e: HttpException) {
        Log.e("serviceError", e.message.toString())
        val errorMessage = if (e.code() == UNAUTHORIZED_ERROR_CODE) {
            R.string.wrong_user_or_password
        } else {
            R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    } catch (e: Exception) {
        Log.e("serviceError", e.message.toString())
        val errorMessage = R.string.unknown_error
        ApiResponseStatus.Error(errorMessage)
    }
}