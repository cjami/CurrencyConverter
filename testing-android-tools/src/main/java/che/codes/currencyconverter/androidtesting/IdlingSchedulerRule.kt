package che.codes.currencyconverter.androidtesting

import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class IdlingSchedulerRule : TestRule {

    companion object {
        fun clearIdlingScheduler(){
            RxJavaPlugins.setIoSchedulerHandler { IoScheduler() }
        }
    }

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("IO Scheduler"))

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                }
            }
        }
    }
}