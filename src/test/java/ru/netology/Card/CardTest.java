package ru.netology.Card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardTest {

    @BeforeAll
    static void SetUpAll(){
        System.setProperty("webdriver.chrome driver","./driver/chromedriver.exe");
    }



    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @Test
    void shouldGetDeliveryCard() {

        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань"); //data-test-id="city"
        String date = generateDate(3);   // генерация даты — не ранее трёх дней с текущей даты
        SelenideElement data = $("[data-test-id='date'] input"); //data-test-id="date"
        data.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.setValue(date);
        $("[data-test-id='name'] input").setValue("Гончаров Артем");
        $("[data-test-id='phone'] input").setValue("+79270070707");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(20));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(20));

    }

}
