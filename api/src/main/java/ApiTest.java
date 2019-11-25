import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jcabi.matchers.RegexMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import java.util.List;
import static com.jayway.restassured.RestAssured.given;


public class ApiTest{

    @Test
    public void firstTest() {
   
        Response response = given()
                .get("https://msk.tele2.ru/api/roaming/regions?siteId=siteMSK")
                .then()
                .contentType("application/json")
                .extract()
                .response();

//Запись всех значений regionId в массив
        List<String> regionIdList = response.jsonPath().getList("data.regionId");
        String[] regionIdArray = regionIdList.toArray(new String[0]);

//Проверка, что все значения regionId являются числами
        for (int i = 0; i < regionIdArray.length; i++){
            MatcherAssert.assertThat(regionIdArray[i], RegexMatchers.matchesPattern("\\d+"));
        }
        //Записываем все значения roamingProductId в массив
        List<String> roamingProductIdList = response.jsonPath().getList("data.roamingProductId");
        String[] roamingProductArray = roamingProductIdList.toArray(new String[roamingProductIdList.size()]);

//Проверка, что все значения roamingProductId соответствую шаблону prod
        for (int i = 0; i < roamingProductArray.length; i++){
            MatcherAssert.assertThat(roamingProductArray[i], RegexMatchers.matchesPattern("prod.+"));
        }
    }
}



