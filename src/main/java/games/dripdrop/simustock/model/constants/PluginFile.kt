package games.dripdrop.simustock.model.constants

enum class PluginFile(val fileName: String) {
    CONFIG_FILE("config.json"),
    COMPANY_LIST_FILE("companies.json"),
    LOCALIZATION_FILE("localization.json"),
    DATABASE_FILE("simustock.db")
}