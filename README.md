## 🔎 프로그램 설명
이 프로젝트는 이미지 검색 및 북마크 기능을 제공하는 애플리케이션입니다. 사용자는 이미지를 검색하고 원하는 이미지를 북마크에 추가할 수 있습니다.

## 📂 폴더 및 파일 구조
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
    - `FavoriteImageAdapter` : 북마크 화면에서 사용하는 리사이클러뷰 어댑터
    - `FavoritesFragment` : 북마크 화면 UI 및 로직 처리
    - `FavoritesViewModel` : 북마크 화면의 데이터 및 로직 처리를 담당
  - `search/`
    - `ImageSearchAdapter` : 이미지 검색 화면에서 사용하는 리사이클러뷰 어댑터
    - `ImageSearchFragment` : 이미지 검색 화면 UI 및 로직 처리
    - `ImageSearchViewModel` : 이미지 검색 화면의 데이터 및 로직 처리를 담당
      
- `network/`
    - `ImageNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `VideoNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `NetWorkClient` : Retrofit 인스턴스 및 설정 관련 로직

- `factory/` : Repository 생성을 위한 Factory 클래스
    - `FavoriteImageViewModelFactory`
    - `ImageSearchViewModelFactory`
- `repository/` : ViewModel 생성을 위한 Repository 클래스
    - `ImageRepository`
    - `PreferencesRepository`
 
- `utils/` : 프로젝트 전반에 사용되는 유틸리티 함수 모음
    - `FormatManager` : 날짜 형식 변경, 이미지 불러오는 함수 관리
    - `UtilityKeyboard` : 키보드 숨기는 함수
 
- `MainActivity` : 애플리케이션의 메인 액티비티
- `ViewPager` : 뷰페이저 관련 함수
- `SharedViewModel` : 여러 Fragment 간에 공유되는 데이터 관리
