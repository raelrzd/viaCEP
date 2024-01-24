package rezende.israel.viacep.di.modules

import org.koin.dsl.module
import rezende.israel.viacep.repository.CepRepository
import rezende.israel.viacep.ui.viewmodel.ActivitiesDeBuscaViewModel
import rezende.israel.viacep.webclient.service.CepService
import rezende.israel.viacep.webclient.RetrofitInicializador

val appModules = module {

    single<CepService> {
        RetrofitInicializador().cepService
    }

    single<CepRepository> {
        CepRepository(get<CepService>())
    }

    single<ActivitiesDeBuscaViewModel> {
        ActivitiesDeBuscaViewModel(get<CepRepository>())
    }
}
