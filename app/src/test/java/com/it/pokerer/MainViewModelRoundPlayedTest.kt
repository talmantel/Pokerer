package com.it.pokerer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.it.pokerer.data.Player
import com.it.pokerer.data.Repository
import com.it.pokerer.ui.main.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.*


@RunWith(value = Parameterized::class)
class MainViewModelRoundPlayedTest (
    private val winner: Player,
    private val gilBet: Int?,
    private val talBet: Int?,
    private val shayBet: Int?,
    private val gilInitialScore: Int,
    private val talInitialScore: Int,
    private val shayInitialScore: Int,
    private val gilExpectedScore: Int?,
    private val talExpectedScore: Int?,
    private val shayExpectedScore: Int?
) {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockGilObserver: Observer<Int>
    private lateinit var mockTalObserver: Observer<Int>
    private lateinit var mockShayObserver: Observer<Int>
    private lateinit var mockLastBetsObserver: Observer<Map<Player, Int>>
    private lateinit var mockRepository: Repository
    private lateinit var model: MainViewModel

    @Before
    fun setUp() {
        mockRepository = mock()
        model = MainViewModel(mockRepository)

        mockGilObserver = mock()
        mockTalObserver = mock()
        mockShayObserver = mock()
        mockLastBetsObserver = mock()
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}")
        fun data(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(
                    Player.TAL, //winner
                    55, 0, 0,    //bets
                    100, 0, 0,   //initial score
                    45, 55, 0//expected score
                ),
                arrayOf(
                    Player.GIL, //winner
                    55, 50, 30,    //bets
                    10, -100, 20,   //initial score
                    90, -150, -10//expected score
                )
            )
        }
    }

    @Test
    fun test_roundPlayed() {
        val gilExpectedLastBet: Int? = when (winner == Player.GIL) {
            true -> gilInitialScore- gilExpectedScore!!
            false -> if(gilBet == 0) null else gilBet
        }
        val talExpectedLastBet: Int? = when (winner == Player.TAL) {
            true -> talInitialScore - talExpectedScore!!
            false -> if(talBet == 0) null else talBet
        }
        val shayExpectedLastBet: Int? = when (winner == Player.SHAY) {
            true -> shayInitialScore - shayExpectedScore!!
            false -> if(shayBet == 0) null else shayBet
        }

        `when`(mockRepository.getPlayerScore(Player.GIL))
            .thenReturn(gilInitialScore)
        `when`(mockRepository.getPlayerScore(Player.TAL))
            .thenReturn(talInitialScore)
        `when`(mockRepository.getPlayerScore(Player.SHAY))
            .thenReturn(shayInitialScore)

        model.getPlayerScore(Player.GIL)!!.observeForever(mockGilObserver)
        model.getPlayerScore(Player.TAL)!!.observeForever(mockTalObserver)
        model.getPlayerScore(Player.SHAY)!!.observeForever(mockShayObserver)
        model.lastBets.observeForever(mockLastBetsObserver)

        model.roundPlayed(
            bets = mapOf(
                Pair(Player.GIL, gilBet ?: 0),
                Pair(Player.TAL, talBet ?: 0),
                Pair(Player.SHAY, shayBet ?: 0)
            ),
            winner = winner
        )

        if(gilExpectedScore != null)
            verify(mockGilObserver).onChanged(eq(gilExpectedScore))
        else
            verify(mockGilObserver, never()).onChanged(any())

        if(talExpectedScore != null)
            verify(mockTalObserver).onChanged(eq(talExpectedScore))
        else
            verify(mockTalObserver, never()).onChanged(any())

        if(shayExpectedScore != null)
            verify(mockShayObserver).onChanged(eq(shayExpectedScore))
        else
            verify(mockShayObserver, never()).onChanged(any())


        val expectedLastBets = mutableMapOf<Player, Int>().apply {
            gilExpectedLastBet?.let { put(Player.GIL, it) }
            talExpectedLastBet?.let { put(Player.TAL, it) }
            shayExpectedLastBet?.let { put(Player.SHAY, it) }
        }
        verify(mockLastBetsObserver).onChanged(eq(expectedLastBets))
    }
}
