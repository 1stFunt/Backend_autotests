package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetDepoProdsData {

    private String type;            // Тип депозитного продукта ('timeDeposit' или 'savingsDeposit')
    private String category;        // Категория депозитного продукта ('active', 'archive', 'unpublished')
    private String dateFrom;        // Дата "от" (формат YYYY-MM-DD)
    private String dateTo;          // Дата "до" (формат YYYY-MM-DD)
    private String name;            // Название продукта (до 30 символов)
    private String currency;        // Валюта продукта (например, 'RUB, EUR, USD')
    private Integer limit;          // Ограничение на количество записей (15)
    private Integer page;           // Номер страницы для пагинации
    private String field;           // Поле для сортировки ('startDate', 'interestRate', 'amountMin', 'amountMax')
    private String order;           // Порядок сортировки (например, 'asc', 'desc')
}