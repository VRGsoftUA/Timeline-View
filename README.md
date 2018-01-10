# Timeline View
<img src="https://github.com/VRGsoftUA/Timeline-View/blob/master/timeline2.gif" alt="alt text" style="width:200;height:200">

#### [HIRE US](http://vrgsoft.net/)

# Usage

*For a working implementation, Have a look at the Sample Project - app*

1. Include the library as local library project.
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
compile 'com.github.VRGsoftUA:Timeline-View-Kotlin:1.0'
}
```
2. Include the YearLayout widget in your layout.

	```xml
      <net.vrgsoft.timelineviewkotlin.YearLayout
        android:id="@+id/year_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/transparent"
        android:scrollbarStyle="insideOverlay"
        android:scrollbars="horizontal"
        app:description_text_size="8sp"
        app:row_text_size="14sp"
        app:year_text_size="9sp"
        />
    ```
3 You need to implement YearModel interface in your model
```java
  public class SomeModel implements YearModel
  ```
  
4. In your `onCreate` method refer to the View and setup YearLayout.Builder.Also you can implement OnRowClickListener for handling clicks 
	```kotlin
    
        val builder = YearLayout.Builder()
        builder.setYears(mModels as ArrayList<YearModel>)
                .setMaxYear(2017)
                .setMinYear(1860)
                .attachToActivity(this)
                .setOnRowClick(this)
                .setYearBackgroundColor(R.color.line_color)
                .setYearTitleColor(R.color.colorPrimary)
                .setYearRowTextColor(R.color.colorPrimaryDark)
                .create()
        mYearLayout!!.setBuilder(builder)
    }

    override fun onClick(year: Int, view: ClickView.ItemHolder) {
        Toast.makeText(this, "Clicked on year " + year, Toast.LENGTH_SHORT).show()
    }
     ```
#### Customisation 
You can add fields via xml or via Builder.
     
Supported fields:

| Field  | Type |
| ------------- | ------------- |
| year_text_size | dimension |
| row_text_size | dimension |
| description_text_size | dimension |
| year_background_color | color |
| year_title_color | color |
| year_row_text_color | color |
| min_year | integer |
| max_year | integer |

 #### Contributing
* Contributions are always welcome
* If you want a feature and can code, feel free to fork and add the change yourself and make a pull request
