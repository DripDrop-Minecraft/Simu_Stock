package games.dripdrop.simustock.center

import games.dripdrop.simustock.interfaces.IExchange
import games.dripdrop.simustock.interfaces.ISecuritiesDealer

class SystemService {
    private val mExchange: IExchange = SystemExchange()
    private val mSecuritiesDealer: ISecuritiesDealer = SystemSecuritiesDealer()
}