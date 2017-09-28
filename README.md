# Weather app 
[APK FILE](https://github.com/astux7/weather-app/tree/master/app/release)
### Idea of the App
<br /><br />
***Enter city page***<br />
<img src="https://github.com/astux7/weather-app/blob/master/pic/Screenshot_20170928-014322.png" height="25%" width="25%"/>
<br /><br />
***Current forecast***<br />
<img src="https://github.com/astux7/weather-app/blob/master/pic/Screenshot_20170928-014328.png" height="25%" width="25%"/>
<br /><br />
***Favourite cities***<br />
<img src="https://github.com/astux7/weather-app/blob/master/pic/Screenshot_20170928-014304.png" height="25%" width="25%"/>
<br /><br />
***STORY***
 - when the user first time opens app - is redirected to `Enter city` page
 - when the user inputs the city - sees the page with `current forecast` for the city
 - when the user clicks button *back*
     - if where is no any favourite cities - going to be redirected to `Enter city` page
     - if where is 1+ favourite cities - going to be redirected to `Favourite cities` page
  - when the user clicks button *Add to Favourites* the city will be added to favourite city page list and will show all the list with forecast
   



### Kotlin libs & features used:
- Recycle View - for list favourite locations
- Retrofit for JSON load
- SqLite for location storage
- data class
- ProgressDialog which is depricated (need to look for replacement)


### Testing

- Started testing adapter (only 1 from 3 test passes) , PR --> https://github.com/astux7/weather-app/pull/9/files (have not finished yet)

- Tried to do unit test [see here PR](https://github.com/astux7/weather-app/pull/8/files) database handler class [by source](https://medium.com/@elye.project/android-sqlite-database-unit-testing-is-easy-a09994701162) and could not finished because:
   + failed to import right version of robolectic to run `@RunWith(RobolectricGradleTestRunner::class)`
   + could not fixed the probably related issue with android studio [here](https://github.com/robolectric/robolectric/wiki/Running-tests-in-Android-Studio)
   + testing other way got error `Method getWritableDatabase in android.database.sqlite.SQLiteOpenHelper not mocked` and could not find the way to fix it yet


### Running app
Android studio 

### Future
- Fix the [issues](https://github.com/astux7/weather-app/issues)
- Refactor code for better testing experience (now is too big activities) 

