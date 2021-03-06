package com.peargrammers.flatfinder

import android.app.Application
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.db.OfferDatabase
import com.peargrammers.flatfinder.repository.AuthRepository
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.repository.ProfileRepository
import com.peargrammers.flatfinder.repository.UserOffersRepository
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModelProviderFactory
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModelProviderFactory
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModelProviderFactory
import com.peargrammers.flatfinder.ui.viewmodel.UserOfferViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FlatFinderApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@FlatFinderApplication))
        bind() from singleton { OfferDatabase(this@FlatFinderApplication) }
        bind() from singleton { OfferRepository(instance()) }
        bind() from provider {
            OfferViewModelProviderFactory(
                instance(), instance()
            )
        }
        bind() from singleton { AuthRepository() }
        bind() from provider {
            LoginViewModelProviderFactory(
                instance()
            )
        }
        bind() from singleton { ProfileRepository() }
        bind() from singleton { UserPreferencesImpl(instance()) }
        bind() from provider {
            ProfileViewModelProviderFactory(
                instance()
            )
        }
        bind() from singleton { UserOffersRepository() }
        bind() from provider {
            UserOfferViewModelProviderFactory(
                instance()
            )
        }
    }
}