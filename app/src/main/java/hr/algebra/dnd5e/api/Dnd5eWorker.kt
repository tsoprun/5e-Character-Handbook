package hr.algebra.dnd5e.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class Dnd5eWorker(
    private val context: Context,workerParams: WorkerParameters, )
    : Worker(context, workerParams) {
    override fun doWork(): Result{
        Dnd5eFetcher(context).fetchReferenceData()
        return Result.success()
    }
}