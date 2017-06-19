# Timeline View
<img src="https://github.com/VRGsoftUA/Timeline-View/blob/master/timeline1.gif" alt="alt text" style="width:200;height:200">

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
compile 'com.github.VRGsoftUA:Timeline-View:-SNAPSHOT'
```
2. Include the YearLayout widget in your layout.

	```xml
      <com.vrgsoft.yearview.YearLayout
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
	```java
    
        YearLayout.Builder builder = new YearLayout.Builder();
        builder.setYears(mModels)
                .setMaxYear(2017)
                .setMinYear(1860)
                .attachToActivity(this)
                .setOnRowClick(this)
                .setYearBackgroundColor(R.color.line_color)
                .setYearTitleColor(R.color.colorPrimary)
                .setYearRowTextColor(R.color.colorPrimaryDark)
                .create();
        mYearLayout.setBuilder(builder);
    }

    @Override
    public void onClick(int year, ClickView.ItemHolder view) {
        Toast.makeText(this, "Clicked on year " + year, Toast.LENGTH_SHORT).show();
    }
     ```
     #### Customisation 
     You can add fields via xml or via Builder.
Supported fields:
     ```xml
        <attr name="year_text_size" format="dimension"/>
        <attr name="row_text_size" format="dimension"/>
        <attr name="description_text_size" format="dimension"/>
        <attr name="year_background_color" format="color"/>
        <attr name="year_title_color" format="color"/>
        <attr name="year_row_text_color" format="color"/>
        <attr name="min_year" format="integer"/>
        <attr name="max_year" format="integer"/>
     ```
     #### Contributing
* Contributions are always welcome
* If you want a feature and can code, feel free to fork and add the change yourself and make a pull request
