package games.dripdrop.simustock.center

import games.dripdrop.simustock.interfaces.IExchange
import games.dripdrop.simustock.interfaces.ISecuritiesDealer

object SystemService {
    private val mExchange: IExchange = SystemExchange()
    private val mSecuritiesDealer: ISecuritiesDealer = SystemSecuritiesDealer()

    fun runSimulatedStockMarket() {
        // TODO
    }

    fun stopSimulatedStockMarket() {
        // TODO
    }
}