package base;

import providers.depositService.*;
import providers.authenticationService.*;
import providers.cardService.GetCardsProvider;
import providers.clientInfoService.GetClientProvider;
import providers.clientInfoService.GetClientsProvider;
import providers.clientInfoService.RegClientProvider;
import providers.creditService.*;
import providers.employeeService.EmployeeInfoProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public abstract class BaseTest {

    private static final Map<Class<?>, Object> providers = new ConcurrentHashMap<>();

    // Создаёт и сохраняет объект провайдера при первом обращении (ленивая инициализация)
    private static <T> T getProvider(Supplier<T> supplier, Class<T> clazz) {
        return clazz.cast(providers.computeIfAbsent(clazz, k -> supplier.get()));
    }

    protected static final AuthProvider AUTH_PROVIDER = getProvider(AuthProvider::new, AuthProvider.class);
    protected static final TokValProvider TOKVAL_PROVIDER = getProvider(TokValProvider::new, TokValProvider.class);
    protected static final GetDepoProdsProvider GET_DEPO_PRODS_PROVIDER = getProvider(GetDepoProdsProvider::new, GetDepoProdsProvider.class);
    protected static final NewCredProdProvider NEW_CRED_PROD_PROVIDER = getProvider(NewCredProdProvider::new, NewCredProdProvider.class);
    protected static final ModCreditProvider MOD_CREDIT_PROVIDER = getProvider(ModCreditProvider::new, ModCreditProvider.class);
    protected static final DeleteCreditProvider DELETE_CREDIT_PROVIDER = getProvider(DeleteCreditProvider::new, DeleteCreditProvider.class);
    protected static final SavingsDepoProdsProvider SAVINGS_DEPO_PRODS_PROVIDER = getProvider(SavingsDepoProdsProvider::new, SavingsDepoProdsProvider.class);
    protected static final DepositConditionsFetcherProvider DEPOSIT_CONDITIONS_FETCHER_PROVIDER = getProvider(DepositConditionsFetcherProvider::new, DepositConditionsFetcherProvider.class);
    protected static final TermDepositProductProvider TERM_DEPOSIT_PRODUCT_PROVIDER = getProvider(TermDepositProductProvider::new, TermDepositProductProvider.class);
    protected static final EmployeeInfoProvider EMPLOYEE_INFO_PROVIDER = getProvider(EmployeeInfoProvider::new, EmployeeInfoProvider.class);
}