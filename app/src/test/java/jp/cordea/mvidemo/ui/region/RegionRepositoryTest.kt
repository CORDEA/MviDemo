package jp.cordea.mvidemo.ui.region

import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RegionRepositoryTest {

    @Mock
    private lateinit var dataSource: RegionRemoteDataSource

    @Mock
    private lateinit var localDataSource: RegionLocalDataSource

    private lateinit var repository: RegionRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RegionRepository(dataSource, localDataSource)

        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    @Test
    fun fetchRegion() {
        whenever(dataSource.fetchRegion()).thenReturn(
                Maybe.just(listOf(mock()))
        )
        whenever(localDataSource.fetchRegion()).thenReturn(
                Maybe.just(listOf(mock(), mock()))
        )
        val test = repository.fetchRegion(true).test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        verify(localDataSource).cacheRegion(Mockito.anyList())
        Truth.assertThat(test.values()[0].size).isEqualTo(1)
    }

    @Test
    fun fetchRegion_with_cache() {
        whenever(dataSource.fetchRegion()).thenReturn(
                Maybe.just(listOf(mock(), mock()))
        )
        whenever(localDataSource.fetchRegion()).thenReturn(
                Maybe.just(listOf(mock()))
        )
        val test = repository.fetchRegion(false).test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        Truth.assertThat(test.values()[0].size).isEqualTo(1)
    }

    @Test
    fun fetchRegion_no_cache() {
        whenever(dataSource.fetchRegion()).thenReturn(
                Maybe.just(listOf(mock()))
        )
        whenever(localDataSource.fetchRegion()).thenReturn(
                Maybe.empty()
        )
        val test = repository.fetchRegion(false).test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        verify(dataSource).fetchRegion()
        Truth.assertThat(test.values()[0].size).isEqualTo(1)
    }
}
