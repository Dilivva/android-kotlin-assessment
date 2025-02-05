package com.solt.deliveryweatherinsighttest.ui.utils

class WeatherDeliveryRecommendations {
    enum class Recommendations(val message:String){
        SAFE_DELIVERY("Safe for delivery") , NOT_TOO_SAFE_FOR_DELIVERY("Delivery can be carried out but weather conditions might change "), DELAY_ADVISED("Weather conditions are not suitable for delivery")
        , NOT_RECOGNIZED("Not recognized weather condition")
    }

    companion object{
        private  fun calculateFor200XCodes(code:Int):Recommendations{
            //These codes represent thunder storms so delay is advised
            return Recommendations.DELAY_ADVISED
        }
        private fun calculateFor300XCodes(code:Int):Recommendations{
            //These codes represent drizzle delivery can be carried out but delay is advised
            return Recommendations.NOT_TOO_SAFE_FOR_DELIVERY
        }
        private fun calculateFor500XCodes(code:Int):Recommendations{
            //These codes represent rains
            //Some conditions delay is advised while some others delivery can also go on
            return  when(code){
                521,520,500 -> Recommendations.NOT_TOO_SAFE_FOR_DELIVERY
                else ->Recommendations.DELAY_ADVISED
            }
        }
        private fun calculateFor600XCodes(code:Int):Recommendations{
            //These codes represent snows
            //Some conditions delay is advised while some others delivery can also go on
            return  when(code){
                600,601,620,621 -> Recommendations.NOT_TOO_SAFE_FOR_DELIVERY
                else ->Recommendations.DELAY_ADVISED
            }
        }

        private fun calculateFor700XCodes(code:Int):Recommendations{
            //These codes represent other atmospheric conditions
            //Some conditions delay is advised while some others delivery can also go on
            return  when(code){
                701,711,721,731,741 -> Recommendations.NOT_TOO_SAFE_FOR_DELIVERY
                else ->Recommendations.DELAY_ADVISED
            }
        }
        private fun calculateFor800XCodes(code:Int):Recommendations{

            return Recommendations.SAFE_DELIVERY
        }
        fun getWeatherDeliveryConditionBasedOnCode(code:Int):Recommendations{
            return if (code in (200..299)){
                calculateFor200XCodes(code)
            }else if (code in (300..399)){
                calculateFor300XCodes(code)
            }else if (code in (500..599)){
                calculateFor500XCodes(code)
            }else if (code in (600..699)){
                calculateFor600XCodes(code)
            }else if (code in (700..799)){
                calculateFor700XCodes(code)
            }else if (code in (800..899)){
                calculateFor800XCodes(code)
            }
            else{Recommendations.NOT_RECOGNIZED}
        }
    }

}
//The system is based on the weather information provided by the weather api
//Check here https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2