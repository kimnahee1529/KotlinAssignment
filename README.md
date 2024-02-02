## 프로그램 설명
이 프로젝트는 이미지 검색 및 북마크 기능을 제공하는 애플리케이션입니다. 사용자는 이미지를 검색하고 원하는 이미지를 북마크에 추가할 수 있습니다.

## 폴더 및 파일 구조
- `data/` : 애플리케이션의 데이터 로직과 API 호출을 처리합니다.
    - `image/`
        - `ImageResponse`
        - `Meta`
        - `Document`
    - `video/`
        - `VideoResponse`
        - `Meta`
        - `Document`
          
- `ui/` : 애플리케이션의 UI를 담당하는 Fragment/Adapter와 viewModel이 위치합니다.
  - `favorites/`
    - `FavoriteImageAdapter`
    - `FavoritesFragment`
    - `FavoritesViewModel`
  - `search/`
    - `ImageSearchAdapter`
    - `ImageSearchFragment`
    - `ImageSearchViewModel`
      
- `network/`
    - `ImageNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `VideoNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `NetWorkClient` : Retrofit 인스턴스 및 설정 관련 로직

- `factory/`
    - `FavoriteImageViewModelFactory`
    - `ImageSearchViewModelFactory`
- `repository/`
    - `ImageRepository`
    - `PreferencesRepository`
 
- `utils/`
    - `FormatManager`
    - `UtilityKeyboard`
 
- `MainActivity`
- `ViewPager`
- `SharedViewModel`
