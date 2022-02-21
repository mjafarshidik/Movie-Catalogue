package com.mjafarshidik.moviecatalogue.utils

import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity


object DataDummy {
    fun getAllMovies(): List<MoviesEntity> {
        val movies = ArrayList<MoviesEntity>()

        movies.add(
            MoviesEntity(
                movieId = null,
                poster = "https://image.tmdb.org/t/p/w185/lNyLSOKMMeUPr1RsL4KcRuIXwHt.jpg",
                id = 580489,
                title = "Venom: Let There Be Carnage",
                date = "2021-09-30",
                score = "6.8",
                overview = "After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.shang_chi}",
                2,
                "Shang-Chi and the Legend of the Ten Rings",
                "2021",
                "72/100",
                "Shang-Chi, the master of weaponry-based Kung Fu, is forced to confront his past after being drawn into the Ten Rings organization."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.black_widow}",
                3,
                "Black Widow",
                "2021",
                "72/100",
                "Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.venom_2}",
                4,
                "Venom: Let There Be Carnage",
                "2021",
                "72/100",
                "Eddie Brock attempts to reignite his career by interviewing serial killer Cletus Kasady, who becomes the host of the symbiote Carnage and escapes prison after a failed execution."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.suicide_squad}",
                5,
                "The Suicide Squad",
                "2021",
                "72/100",
                "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.cruella}",
                6,
                "Cruella",
                "2021",
                "72/100",
                "A live-action prequel feature film following a young Cruella de Vil."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.free_guy}",
                7,
                "Free Guy",
                "2021",
                "72/100",
                "A bank teller discovers that he's actually an NPC inside a brutal, open world video game."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.spiderman_2}",
                8,
                "Spider-Man: Far from Home",
                "2019",
                "72/100",
                "Following the events of Avengers: Endgame (2019), Spider-Man must step up to take on new threats in a world that has changed forever."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.the_social_dilemma}",
                9,
                "The Social Dilemma",
                "2020",
                "72/100",
                "Explores the dangerous human impact of social networking, with tech experts sounding the alarm on their own creations."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.parasite}",
                10,
                "Parasite",
                "2019",
                "72/100",
                "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan."
            )
        )

        movies.add(
            MoviesEntity(
                null,
                "${R.drawable.captain_marvel}",
                11,
                "Captain Marvel",
                "2019",
                "72/100",
                "Carol Danvers becomes one of the universe's most powerful heroes when Earth is caught in the middle of a galactic war between two alien races."
            )
        )

        return movies
    }

    fun getAllTvShows(): List<TVShowsEntity> {
        val tvShows = ArrayList<TVShowsEntity>()

        tvShows.add(
            TVShowsEntity(
                tvId = null,
                poster = "https://image.tmdb.org/t/p/w185/iF8ai2QLNiHV4anwY1TuSGZXqfN.jpg",
                id = 3256397,
                title = "Chucky",
                date = "2021-10-12",
                score = "8.0",
                overview = "After a vintage Chucky doll turns up at a suburban yard sale, an idyllic American town is thrown into chaos as a series of horrifying murders begin to expose the town’s hypocrisies and secrets. Meanwhile, the arrival of enemies — and allies — from Chucky’s past threatens to expose the truth behind the killings, as well as the demon doll’s untold origins."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.squid_game}",
                1,
                "Squid Game",
                "2021",
                "66/100",
                "Hundreds of cash-strapped players accept a strange invitation to compete in children's games. Inside, a tempting prize awaits with deadly high stakes. A survival game that has a whopping 45.6 billion-won prize at stake."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.money_heist}",
                2,
                "Money Heist",
                "2017–2021",
                "66/100",
                "An unusual group of robbers attempt to carry out the most perfect robbery in Spanish history - stealing 2.4 billion euros from the Royal Mint of Spain."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.loki}",
                3,
                "Loki",
                "2021",
                "66/100",
                "The mercurial villain Loki resumes his role as the God of Mischief in a new series that takes place after the events of “Avengers: Endgame.”"
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.falcon}",
                4,
                "The Falcon and the Winter Soldier",
                "2021",
                "66/100",
                "Following the events of 'Avengers: Endgame,' Sam Wilson/Falcon and Bucky Barnes/Winter Soldier team up in a global adventure that tests their abilities -- and their patience."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.start_up}",
                5,
                "Start-up",
                "2020",
                "66/100",
                "Young entrepreneurs aspiring to launch virtual dreams into reality compete for success and love in the cutthroat world of Korea's high-tech industry."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.what_if}",
                6,
                "What If...?",
                "2020",
                "66/100",
                "Exploring pivotal moments from the Marvel Cinematic Universe and turning them on their head, leading the audience into uncharted territory."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.wanda_vision}",
                7,
                "WandaVision",
                "2021",
                "66/100",
                "Blends the style of classic sitcoms with the MCU, in which Wanda Maximoff and Vision - two super-powered beings living their ideal suburban lives - begin to suspect that everything is not as it seems."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.alice_in_borderland}",
                8,
                "Alice in Borderland",
                "2020",
                "66/100",
                "A group of bored delinquents are transported to a parallel wasteland as part of a survival game."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.monster_at_works}",
                9,
                "Monsters at Work",
                "2021",
                "66/100",
                "Tylor Tuskman had just graduated from Monster's University at the top of his scare class and has been recruited at Monsters Inc. as a scarer. However, the day before he started, Waternoose was canned. With Mike and Sully now in charge, the whole corporation is transitioning to a joke company. Tylor now needs to figure out how to become a jokester."
            )
        )

        tvShows.add(
            TVShowsEntity(
                null,
                "${R.drawable.family_guys}",
                10,
                "Family Guy",
                "1999",
                "66/100",
                "In a wacky Rhode Island town, a dysfunctional family strive to cope with everyday life as they are thrown from one crazy scenario to another."
            )
        )
        return tvShows
    }
}