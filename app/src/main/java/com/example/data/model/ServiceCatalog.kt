package com.example.data.model

import com.example.R

object ServiceCatalog {
    const val PHONE_NUMBER = "9693870195"
    const val PHONE_NUMBER_INTL = "+919693870195"
    const val PHONE_DISPLAY = "+91 96938 70195"
    const val YOUTUBE_URL = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw"
    const val YOUTUBE_HANDLE = "@balajidjrathsoundtaroli"
    const val BUSINESS_NAME = "BalaJi DJ Rath & Sound Taroli"
    const val LOCATION = "Taroli"

    val PACKAGES = listOf(
        ServicePackage(
            id = "dancer_dj",
            title = "Dancer DJ",
            subtitle = "High-Energy Dance Floor & Sangeet Setup",
            description = "Specially engineered for wedding sangeet, birthday bashes, and indoor/lawn dance parties. Featuring punchy subwoofers, moving head sharpy beams, strobe effects, wireless mics, and Bollywood/Bhojpuri/Commercial hits DJ mixing.",
            startingPrice = 15000.0,
            equipmentIncluded = listOf(
                "4x JBL SRX 728 High-Bass Cabinets",
                "4x Clear Vocal Mid-High Tops",
                "6x Sharpy 10R Moving Head Beams",
                "Pioneer Professional DJ Console",
                "2x Shure UHF Wireless Microphones",
                "Heavy Smoke & Fog Blast Machine"
            ),
            highlights = listOf(
                "Non-stop DJ Performance",
                "Punchy Dance Floor Bass",
                "Moving Head Light Trusses",
                "Perfect for Sangeet & Birthdays"
            ),
            bannerDrawableRes = R.drawable.img_hero_dj
        ),
        ServicePackage(
            id = "dhamal_dj",
            title = "Dhamal DJ",
            subtitle = "Massive Roadshow & Heavy Bass Soundwall",
            description = "High-voltage mega sound system built for grand wedding baraat processions, roadshows, and city festivals. Extreme ground-shaking bass with multi-tier line arrays, laser displays, and cold spark pyrotechnics.",
            startingPrice = 25000.0,
            equipmentIncluded = listOf(
                "8x Dual 18\" Super-Bass Cabinets",
                "6x Line-Array High Output Tops",
                "Crown I-Tech High-Current Amplifiers",
                "Synchronized Multi-Color Laser Show",
                "Cryo CO2 Jet Cannons & Cold Pyros",
                "Dedicated Diesel Generator Backup"
            ),
            highlights = listOf(
                "Earthquake Bass Quality",
                "Complete Roadshow Ready",
                "Laser & Pyro Spectacle",
                "100% Guaranteed Generator Backup"
            ),
            bannerDrawableRes = R.drawable.img_hero_dj
        ),
        ServicePackage(
            id = "decorated_rath",
            title = "Decorated Rath",
            subtitle = "Royal Wedding Chariot & Groom Procession",
            description = "Majestic royal wedding rath (chariot) elaborately decorated with royal velvet fabrics, fresh & exotic floral artistry, sparkling warm/RGB LED contour lights, comfortable velvet throne seating, and synchronized ambient procession sound.",
            startingPrice = 20000.0,
            equipmentIncluded = listOf(
                "Royal Chariot Carriage with Throne",
                "Custom Fresh Floral & Fabric Drapes",
                "High-Illumination Warm & RGB LED Contour",
                "Inbuilt Procession Audio Reinforcement",
                "Dual High-Capacity Battery Backup",
                "Trained Chariot Handler & Crew"
            ),
            highlights = listOf(
                "Grand Royal King-Style Entry",
                "Premium Floral Craftsmanship",
                "Dazzling Night Illumination",
                "Emergency Battery Inverter"
            ),
            bannerDrawableRes = R.drawable.img_decorated_rath
        ),
        ServicePackage(
            id = "orchestra_trolley",
            title = "Orchestra Trolley",
            subtitle = "Mobile Moving Stage with Live Band Platform",
            description = "Heavy-duty custom mobile acoustic trolley with an open stage for live singers, brass players, synthesizer, and dholak performers. Equipped with all-weather canopy, protective railings, and mobile silent generator for religious and cultural yatras.",
            startingPrice = 30000.0,
            equipmentIncluded = listOf(
                "Heavy-Duty Motorized Trolley Platform",
                "Singer Podium & Artist Safety Railings",
                "Wide-Dispersion Column PA Sound System",
                "Silent Diesel Mobile Power Generator",
                "All-Weather Waterproof Canopy",
                "Stage Neon & Moving Wash Lighting"
            ),
            highlights = listOf(
                "Complete Moving Live Band Stage",
                "Accommodates 6-8 Live Musicians",
                "Non-stop Procession Mobility",
                "Road-Safety Inspected & Certified"
            ),
            bannerDrawableRes = R.drawable.img_orchestra_trolley
        ),
        ServicePackage(
            id = "live_jagran_setup",
            title = "Live Jagran Setup",
            subtitle = "Sacred Mata Ki Chowki & Devotional Bhajan Sandhya",
            description = "Acoustically tuned devotional setup for all-night Jagran, Mata Ki Chowki, and spiritual celebrations. Crisp crystal-clear vocal reproduction so every bhajan verse is heard clearly, complete with classical percussion and devotional stage lighting.",
            startingPrice = 18000.0,
            equipmentIncluded = listOf(
                "Roland SPD-20 Pro Octapad & Percussion",
                "Scale-Changer Harmonium & Tuned Dholak",
                "4x Shure Beta 58A Devotional Vocal Mics",
                "16-Channel Low-Noise Digital Mixer",
                "Spiritual Backdrop Stage Wash Lighting",
                "Devotional Red Stage Carpets & Stage Monitors"
            ),
            highlights = listOf(
                "Crystal-Clear Bhakti Vocals",
                "Complete Instrumental Kit Included",
                "Devotional Stage Ambience",
                "Dedicated Sound Engineer All Night"
            ),
            bannerDrawableRes = R.drawable.img_rath_jagran
        )
    )

    val EVENT_TYPES = listOf(
        "Wedding (Baraat / Sangeet / Reception)",
        "Birthday & Anniversary Party",
        "Cultural Celebration & Festival",
        "Live Jagran / Mata Ki Chowki",
        "Procession / Shobha Yatra / Roadshow",
        "Corporate / Public Event"
    )

    val TIME_SLOTS = listOf(
        "Evening (6:00 PM - 12:00 AM)",
        "Full Night (8:00 PM - 4:00 AM)",
        "Daytime (10:00 AM - 4:00 PM)",
        "Full Day (24 Hours)"
    )

    val STANDARD_CHECKLIST_KEYS = listOf(
        "sound" to "Sound System & Amps Tested",
        "genset" to "Generator Fuel & Oil Level Inspected",
        "rath_trolley" to "Rath / Trolley Mechanics & Battery Ready",
        "lights" to "Moving Heads & Laser Trusses Verified",
        "driver" to "Transport Vehicle & Driver Assigned",
        "dispatch" to "Final Departure & Timely Delivery Confirmed"
    )
}
