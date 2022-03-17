package com.mohamedabdelaziz.coroutines.suspend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mohamedabdelaziz.coroutines.R
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlin.math.log
import kotlin.system.measureTimeMillis

// 1-suspend fun
// 2-Coroutines Builder - runBlocking
// 3-Async & Await
// 4-Jobs
// 5-Channels & Flows
// 6-State Flow
class MainActivity : AppCompatActivity() {

    //1- stop/resume without suspend
    private fun functionA(a: Int) {
        when (a) {
            1 -> {
                Log.d("function A", "do one of A ")
                functionB(1)
            }
            2 -> {
                Log.d("function A", "do two of A ")
                functionB(2)
            }
            3 -> {
                Log.d("function A", "do three of A ")
                functionB(3)
            }
        }
    }
    private fun functionB(b: Int) {
        when (b) {
            1 -> {
                Log.d("function B", "do one of B ")
                functionA(2)
            }
            2 -> {
                Log.d("function B", "do two of B ")
                functionA(3)
            }
            3 -> {
                Log.d("function B", "do three of B ")
            }
        }
    }

//--------------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
      functionA(1)
*/
//--------------------------------------------------------------------------------------------------------
        // 2-Coroutines Builder (scope-start-thread-body)
        //scope

      /*   GlobalScope.launch {
             //currentThread
             Log.d("Coroutines Builder", "current Thread: ${Thread.currentThread().name}")
             printTextAfterDelayA("A")
         }
*/
        //thread

       /* GlobalScope.launch(Dispatchers.IO) {
            Log.d("Coroutines Builder", "current Thread: ${Thread.currentThread().name}")
            printTextAfterDelayA("A")
        }
*/
        // runBlocking

      /*  runBlocking {
              printTextAfterDelayA("A")
          } */
//--------------------------------------------------------------------------------------------------------
        // run 2 suspend fun in 1 Coroutines Builder (parallel)

      /*    GlobalScope.launch {
               printTextAfterDelayA("Ahmad")
               printTextAfterDelayA("Mohamed")
          }
*/
        // solved : run 2 suspend fun (concurrency)
       /* GlobalScope.launch {
            launch { printTextAfterDelayA("Ahmad") }
            launch { printTextAfterDelayA("Mohamed") }
        }*/
//--------------------------------------------------------------------------------------------------------
        // 3-Async & Await
        /*  GlobalScope.launch {
              val time = measureTimeMillis {
                  Log.d("Async & Await", "before")
//              val remoteData =fetchDataFromNetwork()
//              val localData =fetchDataFromData()
//                  if (remoteData==localData)
//                      Log.d("Async & Await", "equals")
//                  else
//                      Log.d("Async & Await", "not equals")


                  var remoteData = async { fetchDataFromNetwork() }
                  var localData = async { fetchDataFromData() }

                  if (remoteData.await() == localData.await())
                      Log.d("Async & Await", "equals")
                  else
                      Log.d("Async & Await", "not equals")
              }
              Log.d("Async", time.toString())
          }*/

//--------------------------------------------------------------------------------------------------------
// 4-Jobs
          val parentJob:Job= Job()
//
//          val job1:Job= GlobalScope.launch (parentJob){
//              launch { printTextAfterDelayA("Ahmad") }
//              launch { printTextAfterDelayA("Mohamed") }
//          }
//          job1.cancel()

//        val job2:Job= GlobalScope.launch(parentJob) {
////            printTextAfterDelayA("33333")
//            val child1=launch { printTextAfterDelayA("Ahmad") }
//              val child2=launch { printTextAfterDelayA("Mohamed") }
//
//              child1.
//            printTextAfterDelayA("33333")
////              delay(2000)
//          }
//        parentJob.cancel( )

//--------------------------------------------------------------------------------------------------------
// 5-Channels  (producer-buffer-collector) producer suspend problems.. buffer not send data to collector if no connection

      /*     val channel= Channel<String>()
          val charList= arrayOf("a","b","c","d")

          runBlocking {
               launch {
                   for (char in charList){
                       channel.send(char)
                       Log.d("here: producer", char)

                   }
               }
               launch {
                   for (char in channel){
                       Log.d("here: collector char", char)
                   }
               }
           }*/
        //5-Flow (producer-intermediate-collector)  if no collector producer cannot send data
        //  producer
        runBlocking {
         /*   flow<Int> {
                for (i in 1..10) {
                    emit(i)
                    Log.d("1-flow producer", i.toString() )
                }
            }.filter { i -> i < 5 }//intermediate
*/

            flow<Int> {
                for (i in 1..10) {
                    emit(i)
                    Log.d("flow producer", i.toString() )
                }
            }.filter { i -> i < 5 }//intermediate
                .collect {
                    Log.d("flow collector", it.toString() )
                }
        }
    }

    private suspend fun fetchDataFromData(): String {
        delay(3000)
        return "Mo"
    }

    private suspend fun fetchDataFromNetwork(): String {
        delay(2000)
        return "Mo"
    }


    //--------------------------------------------------------------------------------------------------------
    //1- delay with suspend
    private suspend fun printTextAfterDelayA(text: String) {
        delay(2000)
        Log.d("printTextAfterDelay A", text)
    }

    // delay without suspend
//    private fun printTextAfterDelayB(text: String) {
//        Thread.sleep(2000)
//        Log.d("printTextAfterDelay B", text)
//    }


}