package com.example.data.model

import com.example.R

data class EventSuitabilityInfo(
    val eventName: String,
    val iconEmoji: String,
    val recommendationLevel: String, // "Highly Recommended", "Perfect Fit", "Popular Choice"
    val whySuitable: String
)

data class GalleryItem(
    val id: String,
    val title: String,
    val category: String, // "Royal Procession", "Mobile Stage", "Spiritual & Devotional", "Mega Sound & DJ"
    val imageRes: Int,
    val tagline: String,
    val overview: String,
    val detailedDescription: String,
    val keyFeatures: List<String>,
    val technicalSpecs: List<Pair<String, String>>,
    val eventSuitability: List<EventSuitabilityInfo>,
    val maintenanceAndReadinessNote: String,
    val startingPrice: String,
    val primaryColorHex: Long = 0xFFFFB300
)

object GalleryCatalog {
    val ITEMS = listOf(
        GalleryItem(
            id = "decorated_rath",
            title = "Decorated Rath",
            category = "Royal Procession",
            imageRes = R.drawable.img_decorated_rath,
            tagline = "Royal Ceremonial Chariot with Fresh Floral Craft & Sparkling LEDs",
            overview = "An opulent, majestic ceremonial chariot designed to give the bride or groom a royal, king-like procession entry. Handcrafted with traditional golden ornamental carvings, fresh fragrant flower garlands, and high-illumination warm-white and RGB contour LEDs.",
            detailedDescription = "The BalaJi DJ Decorated Rath is our flagship royal attraction for Indian weddings and celebratory roadshows. The chariot features a grand velvet throne platform, gold-gilded ornate domes, and extensive floral art combining fresh marigold, roses, and orchids with durable fabric drapes. It is fitted with built-in hidden sound speakers for synchronized background music and chanting, along with independent twin heavy-duty battery inverters to ensure 100% uninterrupted illumination throughout the entire procession.",
            keyFeatures = listOf(
                "Ornate golden ceremonial carriage with royal velvet cushioned throne",
                "Fresh floral cascading garlands with roses, marigolds, and exotic greens",
                "360-degree dynamic warm-white and programmable RGB LED contour strips",
                "Integrated 1000W hidden PA sound system for groom entry music",
                "Twin high-amperage battery inverter backup (no messy cables on road)",
                "Smooth pneumatic wheels and suspension for effortless road mobility",
                "Dedicated trained chariot handlers and technician for on-site management"
            ),
            technicalSpecs = listOf(
                "Carriage Dimensions" to "11 ft (L) x 6.5 ft (W) x 9 ft (H)",
                "Seating Capacity" to "1 Groom / Royal Couple + 1 Attendant",
                "Lighting Power" to "450W Custom LED Arrays + Golden Neon accents",
                "Audio Reinforcement" to "Inbuilt 1000W Column Audio with Bluetooth / Mixer feed",
                "Power System" to "Dual 150Ah Deep-Cycle Inverter Batteries (6+ Hours)",
                "Weight Capacity" to "Supports up to 350 kg payload smoothly"
            ),
            eventSuitability = listOf(
                EventSuitabilityInfo(
                    eventName = "Weddings (Baraat & Royal Entry)",
                    iconEmoji = "👑",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Creates a show-stopping, unforgettable grand entrance for the groom during the Baraat procession and Jaimala arrival."
                ),
                EventSuitabilityInfo(
                    eventName = "Cultural & Religious Shobha Yatras",
                    iconEmoji = "🚩",
                    recommendationLevel = "Perfect Fit",
                    whySuitable = "Provides an auspicious, elevated platform for deity idols, spiritual gurus, and temple festival celebrations."
                ),
                EventSuitabilityInfo(
                    eventName = "Milestone Anniversaries & Receptions",
                    iconEmoji = "✨",
                    recommendationLevel = "Popular Choice",
                    whySuitable = "Adds a touch of royal splendor and memorable photography backdrop for silver and golden jubilee celebrations."
                )
            ),
            maintenanceAndReadinessNote = "Inspected before every booking: Battery health tested, tire pressure verified, floral framing sanitized, and electrical wiring tested for 100% timely delivery.",
            startingPrice = "₹20,000",
            primaryColorHex = 0xFFFFB300
        ),
        GalleryItem(
            id = "orchestra_trolley",
            title = "Orchestra Trolley",
            category = "Mobile Stage",
            imageRes = R.drawable.img_orchestra_trolley,
            tagline = "Custom Mobile Concert Stage on Wheels with Inbuilt Power & Live Band Setup",
            overview = "A purpose-built motorized musical orchestra trolley engineered for moving street celebrations, yatras, and lively roadshows. Accommodates a full live musical band with sound columns, vocal podiums, and silent power generator.",
            detailedDescription = "When static sound isn't enough, the BalaJi DJ Orchestra Trolley brings the entire concert on the road. Featuring a heavy-duty reinforced chassis, safety railings, weather-resistant canopy, and elevated singer podium, this mobile unit comfortably accommodates 6 to 8 musicians including brass players, synthesizers, octapad, and vocalists. Powered by an onboard soundproof diesel generator and high-throw column line-arrays, it delivers clear, room-filling sound that travels along the procession route without interruption.",
            keyFeatures = listOf(
                "Reinforced motorized mobile stage chassis with non-slip flooring",
                "Spacious musician platform accommodating 6–8 live instrumentalists and singers",
                "Heavy-duty stainless steel artist safety railings and elevated podium",
                "High-throw column speaker array with crystal-clear voice projection",
                "Onboard silent Kirloskar diesel generator for non-stop mobile electricity",
                "All-weather heavy-duty waterproof canopy protecting equipment from rain and sun",
                "Vibrant dynamic neon wash fixtures and strobe lights mounted on stage trusses",
                "Full live mixing console with multi-channel snake and wireless monitor feeds"
            ),
            technicalSpecs = listOf(
                "Platform Dimensions" to "18 ft (L) x 8 ft (W) x 10.5 ft (H)",
                "Musician Capacity" to "Up to 8 Live Artists + Instruments",
                "Audio Output" to "8000W RMS Mobile Line-Array Columns + Subwoofers",
                "Power Generator" to "Onboard 25 kVA Silent Soundproof Diesel Genset",
                "Lighting Rig" to "LED Par Cans + RGB Wash Bars + Moving Heads",
                "Road Safety" to "Certified dual braking system, warning beacons, and reflectors"
            ),
            eventSuitability = listOf(
                EventSuitabilityInfo(
                    eventName = "Cultural Celebrations & Nagar Kirtans",
                    iconEmoji = "🥁",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Enables live kirtan, bhajan mandalis, and devotional brass bands to sing and play continuously through city streets."
                ),
                EventSuitabilityInfo(
                    eventName = "Grand Wedding Roadshows",
                    iconEmoji = "🎺",
                    recommendationLevel = "Perfect Fit",
                    whySuitable = "Combines traditional live shehnai / brass musicians with modern mobile DJ sound for an electrifying baraat atmosphere."
                ),
                EventSuitabilityInfo(
                    eventName = "Festival Processions (Durga Puja, Ganesh Visarjan)",
                    iconEmoji = "🎉",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Offers non-stop musical energy and crowd interaction across miles of road travel without generator outages."
                )
            ),
            maintenanceAndReadinessNote = "Chassis mechanical inspection, generator oil/fuel level check, speaker rigging safety review, and sound acoustic test conducted 24 hours prior to dispatch.",
            startingPrice = "₹30,000",
            primaryColorHex = 0xFFFF6F00
        ),
        GalleryItem(
            id = "live_jagran_setup",
            title = "Live Jagran Setup",
            category = "Spiritual & Devotional",
            imageRes = R.drawable.img_rath_jagran,
            tagline = "Sacred Mata Ki Chowki & Devotional Bhajan Sandhya Acoustic System",
            overview = "A dedicated spiritual sound and stage acoustic package tuned specifically for devotional all-night Jagran, Mata Ki Chowki, and Bhajan Sandhya. Includes classical instruments, crystal mics, and warm devotional stage ambience.",
            detailedDescription = "Devotional bhajan music demands extraordinary vocal clarity so every devotional verse, shloka, and stuti resonates deeply with devotees. The BalaJi DJ Live Jagran Setup is acoustically calibrated for low-noise, high-fidelity vocal reproduction with zero feedback hum. The package includes professional Roland SPD-20 Pro octapad percussion, calibrated harmonium, tuned dholak, four premium Shure Beta 58A vocal mics, devotional red stage carpeting, and warm golden backdrop illumination, all managed by a dedicated sound engineer from dusk to dawn.",
            keyFeatures = listOf(
                "Roland SPD-20 Pro digital octapad with rich devotional patch presets",
                "Custom tuned Teakwood scale-changer harmonium and classical dholak",
                "4x Shure Beta 58A vocal microphones for lead and chorus singers",
                "16-channel low-noise digital mixer with warm reverb and compression",
                "Ultra-wide low-profile stage floor monitors so singers hear every pitch clearly",
                "Sacred devotional stage red carpeting and velvet bolster cushions",
                "Warm spiritual backdrop lighting and deity spotlight illumination",
                "Dedicated sound engineer on-site for the entire night (dusk to morning aarti)"
            ),
            technicalSpecs = listOf(
                "Acoustic Coverage" to "Comfortably covers 200 to 2,500 devotees indoors or lawn",
                "Microphone Kit" to "4x Shure Beta 58A + 2x Shure SM57 (Instrument) + 2x Wireless Handhelds",
                "Mixer Console" to "Yamaha 16-Channel Digital Audio Console with DSP",
                "Stage Dimensions" to "Configurable for 16x12 ft to 24x16 ft devotional stage",
                "Vocal Clarity" to "Tuned vocal eq with anti-feedback DSP filters",
                "Operating Duration" to "Up to 12 Hours continuous all-night coverage"
            ),
            eventSuitability = listOf(
                EventSuitabilityInfo(
                    eventName = "Mata Ki Chowki & All-Night Jagrans",
                    iconEmoji = "🕉️",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Designed specifically for devotional bhajan mandalis with flawless vocal clarity and zero microphone screeching."
                ),
                EventSuitabilityInfo(
                    eventName = "Bhajan Sandhya & Khatu Shyam Kirtan",
                    iconEmoji = "🪔",
                    recommendationLevel = "Perfect Fit",
                    whySuitable = "Creates a deeply spiritual, reverent ambience with warm golden lighting and crisp percussion playback."
                ),
                EventSuitabilityInfo(
                    eventName = "Cultural Celebrations & Shiv Charcha",
                    iconEmoji = "🌺",
                    recommendationLevel = "Popular Choice",
                    whySuitable = "Provides complete musical and sound infrastructure for community gatherings and sacred ceremonies."
                )
            ),
            maintenanceAndReadinessNote = "Microphone capsules sanitized and tested, octapad strike pads calibrated, audio cables continuity-checked, and mixer channels pre-configured before delivery.",
            startingPrice = "₹18,000",
            primaryColorHex = 0xFF7C4DFF
        ),
        GalleryItem(
            id = "dhamal_dj",
            title = "Dhamal DJ Soundwall",
            category = "Mega Sound & DJ",
            imageRes = R.drawable.img_hero_dj,
            tagline = "High-Voltage Roadshow Sound System with Earthquake Bass & Laser Show",
            overview = "The ultimate high-impact roadshow setup built for maximum excitement during grand wedding baraats, festival processions, and mega celebratory rallies. Delivers ground-shaking bass and cutting-edge special effects.",
            detailedDescription = "Our Dhamal DJ setup is engineered for clients who want uncompromising volume, thumping deep sub-bass, and a stadium-level party spectacle. Featuring eight dual-18\" super-bass cabinets powered by Crown high-current amplifiers, a full aluminum lighting truss with 10R beam sharpies, multi-color synchronized lasers, CO2 cryo jets, and cold spark pyrotechnics, this package transforms any city street into a thrilling outdoor dance carnival.",
            keyFeatures = listOf(
                "8x Dual 18-inch high-excursion subwoofers for earth-shaking sub-bass",
                "6x High-output line array mid-top cabinets for crisp distant projection",
                "Crown I-Tech power amplifier racks delivering 20,000W+ pure RMS power",
                "Dual high-power multi-beam laser projectors with animated pattern sync",
                "Cryo CO2 jet cannons and indoor/outdoor cold spark fireworks",
                "Heavy smoke and haze blast machines for cinematic laser beam visibility",
                "Heavy-duty mobile trailer and dual generator backup for non-stop performance"
            ),
            technicalSpecs = listOf(
                "Peak Sound Output" to "24,000 Watts Peak / 138 dB Sound Pressure Level",
                "Bass Response" to "Ultra-deep 28 Hz – 90 Hz punch curve",
                "Lighting Grid" to "12x Sharpy 10R Moving Heads + 4x RGB Lasers + 8x LED Blinders",
                "Special Effects" to "4x Cold Pyro Machines + 2x CO2 Jet Guns + 1x Heavy Fogger",
                "Power Requirement" to "Dedicated 45 kVA Kirloskar Silent Generator included",
                "Coverage Reach" to "Over 500 meters of clear procession sound projection"
            ),
            eventSuitability = listOf(
                EventSuitabilityInfo(
                    eventName = "Grand Baraat & Groom Processions",
                    iconEmoji = "🔥",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Delivers maximum energy and unmatched dance vibe for the groom's friends and family throughout the road journey."
                ),
                EventSuitabilityInfo(
                    eventName = "Cultural Celebrations & Visarjan Rallies",
                    iconEmoji = "🎊",
                    recommendationLevel = "Perfect Fit",
                    whySuitable = "Dominates street celebrations with thunderous bass, lasers, and cold pyros that energize thousands of spectators."
                ),
                EventSuitabilityInfo(
                    eventName = "College Fests & Youth Celebrations",
                    iconEmoji = "🎧",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Creates a pulsating festival mainstage feel with live DJ mixing and synchronized beam effects."
                )
            ),
            maintenanceAndReadinessNote = "Voice coils impedance checked, amplifier cooling fans serviced, pyro safety pins verified, and laser alignment inspected prior to timely delivery.",
            startingPrice = "₹25,000",
            primaryColorHex = 0xFFFF3D00
        ),
        GalleryItem(
            id = "dancer_dj",
            title = "Dancer DJ Dancefloor",
            category = "Party & Sangeet",
            imageRes = R.drawable.img_hero_dj,
            tagline = "Electrifying Dance Floor & Sangeet Setup with Moving Heads & Wireless Mics",
            overview = "Specially curated for indoor banquet halls, hotel lawns, wedding Sangeet ceremonies, and birthday dance parties. Crisp vocals, punchy dance floor bass, and intelligent moving lighting.",
            detailedDescription = "The Dancer DJ setup is optimized for indoor and semi-outdoor dance floors where high audio clarity, punchy musical rhythm, and interactive lighting are essential. Powered by four JBL SRX subwoofers, precision mid-tops, Pioneer DJ consoles, and intelligent moving head beam trusses, it ensures high-energy dancing without deafening distortion, allowing guests of all ages to celebrate comfortably.",
            keyFeatures = listOf(
                "4x JBL SRX 728 High-Bass Subwoofer stack for punchy musical beats",
                "4x Vocal-clarity mid-high cabinets for sparkling Bollywood & commercial hits",
                "Pioneer professional digital DJ console with live beat-matching",
                "6x Sharpy 10R moving head beam lights creating nightclub light beams",
                "High-density fog and smoke blast for dramatic dance floor entry",
                "2x Shure UHF wireless handheld microphones for family sangeet announcements"
            ),
            technicalSpecs = listOf(
                "Sound Output" to "8,000 Watts RMS clean digital amplification",
                "Lighting" to "6x Moving Heads + 8x RGB Par Cans + Strobe Controller",
                "Microphones" to "Dual UHF Diversity Wireless Microphones (100m range)",
                "Space Footprint" to "Compact 16x10 ft footprint suitable for banquet stages and lawns",
                "DJ Performance" to "Continuous 5-6 hours DJ mixing with custom playlists"
            ),
            eventSuitability = listOf(
                EventSuitabilityInfo(
                    eventName = "Wedding Sangeet & Cocktail Nights",
                    iconEmoji = "💃",
                    recommendationLevel = "Highly Recommended",
                    whySuitable = "Perfect acoustics for family dance performances, anchoring, and non-stop late-night party dancing."
                ),
                EventSuitabilityInfo(
                    eventName = "Birthday Celebrations & Anniversaries",
                    iconEmoji = "🎂",
                    recommendationLevel = "Perfect Fit",
                    whySuitable = "Fills the party floor with vibrant lights, fog effects, and favorite music hits for all age groups."
                ),
                EventSuitabilityInfo(
                    eventName = "Corporate & Private Lawn Parties",
                    iconEmoji = "🥂",
                    recommendationLevel = "Popular Choice",
                    whySuitable = "Clean, compact aesthetic that fits seamlessly into premium banquet halls and open farmhouses."
                )
            ),
            maintenanceAndReadinessNote = "Microphone batteries freshly replaced, DJ controller firmware updated, cables tested for zero hum, and speaker cones checked for timely delivery.",
            startingPrice = "₹15,000",
            primaryColorHex = 0xFF00E676
        )
    )
}

object VideoCatalog {
    val VIDEOS = listOf(
        EventVideo(
            id = "vid_rath_baraat",
            title = "Royal Wedding Baraat Entry on Decorated Rath",
            hindiTitle = "शाही शादी बारात - सजा हुआ रथ व दूल्हा एंट्री",
            category = "Event Live Videos",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_decorated_rath,
            duration = "04:25",
            viewsCount = "12.4K views",
            description = "Live footage of grand wedding baraat in Taroli featuring our decorated royal wedding rath with golden contour LEDs, fresh flower work and synchronized background music entry.",
            isTutorial = false
        ),
        EventVideo(
            id = "vid_orchestra_trolley_live",
            title = "Orchestra Trolley Moving Roadshow & Brass Band Yatra",
            hindiTitle = "ऑर्केस्ट्रा ट्रॉली लाइव शोभा यात्रा एवं रोड शो",
            category = "Event Live Videos",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_orchestra_trolley,
            duration = "06:12",
            viewsCount = "18.9K views",
            description = "High-energy moving procession with live musicians, vocalists on our motorized stage trolley with onboard silent diesel generator and column PA sound.",
            isTutorial = false
        ),
        EventVideo(
            id = "vid_live_jagran_sandhya",
            title = "Sacred Mata Ki Chowki & All-Night Jagran Live Bhajan",
            hindiTitle = "माता की चौकी एवं संपूर्ण रात्रि जागरण लाइव भजन",
            category = "Event Live Videos",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_rath_jagran,
            duration = "08:40",
            viewsCount = "24.1K views",
            description = "Acoustically tuned divine live Jagran setup with Roland octapad, tuned dholak, harmonium, and crystal clear Shure Beta 58 vocal reproduction.",
            isTutorial = false
        ),
        EventVideo(
            id = "vid_dhamal_dj_roadshow",
            title = "Dhamal DJ Extreme Super-Bass Soundwall Roadshow",
            hindiTitle = "धमाल डीजे मेगा रोड शो - भारी बेस एवं लेजर शो",
            category = "Event Live Videos",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_hero_dj,
            duration = "05:18",
            viewsCount = "31.2K views",
            description = "Earthquake bass test with 8 dual-18 inch cabinets, moving head sharpies, and cold pyro fire display during street baraat procession.",
            isTutorial = false
        ),
        EventVideo(
            id = "tut_rath_lighting",
            title = "Decorated Rath: LED Ribbons & Inverter Maintenance Tutorial",
            hindiTitle = "ट्यूटोरियल: रथ की लाइटिंग एवं इन्वर्टर वायरिंग गाइड",
            category = "Setup Tutorials",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_decorated_rath,
            duration = "07:35",
            viewsCount = "8.6K views",
            description = "Step-by-step tutorial explaining how our technician crew inspects and maintains the dual 150Ah battery inverters and golden LED wiring for zero failure on event night.",
            isTutorial = true
        ),
        EventVideo(
            id = "tut_jagran_mics",
            title = "Live Jagran Setup: Audio Tuning & Mic Balancing Tutorial",
            hindiTitle = "ट्यूटोरियल: जागरण माइक सेटिंग एवं एंटी-सीटी बैलेंस",
            category = "Setup Tutorials",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_rath_jagran,
            duration = "09:10",
            viewsCount = "15.3K views",
            description = "Sound engineering tutorial: How to calibrate stage floor monitors, eliminate feedback screeching, and tune octapad levels for devotional singing.",
            isTutorial = true
        ),
        EventVideo(
            id = "tut_trolley_generator",
            title = "Orchestra Trolley: Mobile Generator & Safety Tutorial",
            hindiTitle = "ट्यूटोरियल: ट्रॉली जनरेटर संचालन एवं रोड सुरक्षा गाइड",
            category = "Setup Tutorials",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_orchestra_trolley,
            duration = "06:45",
            viewsCount = "11.1K views",
            description = "Complete guide on mobile diesel generator load management, stage railing locking, and audio snake connectivity on wheels.",
            isTutorial = true
        ),
        EventVideo(
            id = "tut_soundwall_bass",
            title = "Soundwall Amplifier Wattage & Bass Frequency Testing",
            hindiTitle = "ट्यूटोरियल: एम्पलीफायर वाटेज एवं बेस फ्रीक्वेंसी टेस्ट",
            category = "Setup Tutorials",
            videoUrl = "https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw",
            thumbnailRes = R.drawable.img_hero_dj,
            duration = "08:15",
            viewsCount = "19.7K views",
            description = "Bench-testing Crown power amplifiers and 18-inch subwoofers at 20,000 Watts RMS load to ensure safe distortion-free performance.",
            isTutorial = true
        )
    )
}
