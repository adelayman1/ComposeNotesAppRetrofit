# ComposeNotesAppRetrofit :book: 

<a href="https://www.linkedin.com/in/adel-ayman-2497ab1b3/">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  
 **Notes App** That's Built With Kotlin to keep and organize user's notes. This repository contains a Notes App This is an educational App.Use and run this App to learn more about Apps design and best practices you must follow.That's built with Kotlin language with compose, That's implements Coroutines,Retrofit for HTTP requests,ktor-client for WebSocket requests,clean architecture,hilt,StateFlow,Navigation component,etc... this is client side you can find server side as api built with KTOR here -> [SocketNotesApi](https://github.com/adelayman1/SocketNoteApiKtor/) this project implement http and websocket you can find the same API but without websocket(Http requests only) here ->[HttpNotesApi](https://github.com/adelayman1/HttpNotesApiKtor/)

 ![](https://user-images.githubusercontent.com/85571327/206874180-5b9fb911-c883-49f8-add2-82e8cfad6b37.png)
 
 ## Features
- Login with email and password
- Signup with email and password 
- Search
- Add Notes
- Get Notes with realtime changes
- Edit Notes
- Change Note Color
- Add Link
- Add Image
- Delete Note
- Validate WebLink
- Validate Email And Password
- Validate Empty Fields

 ## App Overview

- in this screen you can signin with email and password

<img src="https://user-images.githubusercontent.com/85571327/206874486-7bdede0b-250e-4536-8274-7657d31aa5be.jpg" width="300" />


- in this screen you can signup with email and password

<img src="https://user-images.githubusercontent.com/85571327/206874501-ac63a916-19f0-4847-8973-b3bc040b00a9.jpg" width="300" />


- this screen view all user notes

<img src="https://user-images.githubusercontent.com/85571327/206873030-6a027f72-55d5-44a8-9421-eb97048598b2.jpg" width="300" />


- this screen view note details and edit note

<img src="https://user-images.githubusercontent.com/85571327/206873063-9960f60d-94cc-48a9-82cb-1949504c4b1e.jpg" width="300" />


**Bottom Sheet Content**

<img src="https://user-images.githubusercontent.com/85571327/206873081-448ed6f2-82ce-4f9c-b715-98dbbb1a1f08.jpg" width="300" />



**Add Link**

<img src="https://user-images.githubusercontent.com/85571327/206873092-b385ca08-3bb7-49df-893a-6678a96a01c7.jpg" width="300" />



**Fields Validation**

<img src="https://user-images.githubusercontent.com/85571327/206873126-2fb55899-47ac-4f26-a7eb-a49b1ee32083.jpg" width="300" />

<img src="https://user-images.githubusercontent.com/85571327/206873147-1b697516-2cde-44cf-b2e9-78b1a0c1ff57.jpg" width="300" />

<img src="https://user-images.githubusercontent.com/85571327/206873170-49e9a939-b4b2-465a-b696-e63bede3eb58.jpg" width="300" />



**Add Image**

<img src="https://user-images.githubusercontent.com/85571327/206873187-ed0bc810-2f90-4bbb-bb34-97700cbae45f.jpg" width="300" />


## Built With 🛠

*  [Kotlin](https://kotlinlang.org/) 
*  [Jetpack Compose](https://developer.android.com/jetpack/compose) 
*  [Coroutines](https://developer.android.com/kotlin/coroutines)
*  [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) 
*  MVVM architecture
*  Clean architecture
*  [Navigation component](https://developer.android.com/guide/navigation)
*  [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) 
*  [Retrofit2](https://square.github.io/retrofit/) 
*  [Ktor-Client](https://ktor.io/docs/getting-started-ktor-client-multiplatform-mobile.html)
*  [Coil](https://coil-kt.github.io/coil/compose/)
*  [Serialization](https://kotlinlang.org/docs/serialization.html/)
*  [Accompanist Permissions](https://github.com/google/accompanist/)
*  [LazyVerticalStaggeredGrid](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/staggeredgrid/package-summary/)
*  Single activity concept 
*  Repository pattern

 ## Project Structure
```
 noteappcompose
 ┣ data
 ┃ ┣ di
 ┃ ┃ ┗ modules
 ┃ ┃ ┃ ┣ CoroutinesModule.kt
 ┃ ┃ ┃ ┣ DatabaseModule.kt
 ┃ ┃ ┃ ┗ RepositoryModule.kt
 ┃ ┣ repositories
 ┃ ┃ ┣ NoteRepositoryImpl.kt
 ┃ ┃ ┗ UserRepositoryImpl.kt
 ┃ ┗ source
 ┃ ┃ ┣ local
 ┃ ┃ ┃ ┗ dataSource
 ┃ ┃ ┃ ┃ ┗ UserLocalDataSource.kt
 ┃ ┃ ┗ remote
 ┃ ┃ ┃ ┣ dataSource
 ┃ ┃ ┃ ┃ ┣ NoteSocketDataSource.kt
 ┃ ┃ ┃ ┃ ┣ NotesRemoteDataSource.kt
 ┃ ┃ ┃ ┃ ┗ UserRemoteDataSource.kt
 ┃ ┃ ┃ ┣ endPoints
 ┃ ┃ ┃ ┃ ┣ NotesApiService.kt
 ┃ ┃ ┃ ┃ ┗ UserApiService.kt
 ┃ ┃ ┃ ┣ requestModels
 ┃ ┃ ┃ ┃ ┣ AddNoteRequestModel.kt
 ┃ ┃ ┃ ┃ ┣ LoginRequestModel.kt
 ┃ ┃ ┃ ┃ ┗ RegisterRequestModel.kt
 ┃ ┃ ┃ ┗ responseModels
 ┃ ┃ ┃ ┃ ┣ BaseApiResponse.kt
 ┃ ┃ ┃ ┃ ┣ NoteResponseModel.kt
 ┃ ┃ ┃ ┃ ┗ UserResponseModel.kt
 ┣ domain
 ┃ ┣ models
 ┃ ┃ ┣ NoteModel.kt
 ┃ ┃ ┣ UserModel.kt
 ┃ ┃ ┗ ValidateResult.kt
 ┃ ┣ repositories
 ┃ ┃ ┣ NoteRepository.kt
 ┃ ┃ ┗ UserRepository.kt
 ┃ ┣ usecases
 ┃ ┃ ┣ AddEditNoteUseCase.kt
 ┃ ┃ ┣ GetAllNotesUseCase.kt
 ┃ ┃ ┣ GetNoteDetailsUseCase.kt
 ┃ ┃ ┣ IsUserSplashUseCase.kt
 ┃ ┃ ┣ LoginUseCase.kt
 ┃ ┃ ┣ RegisterUseCase.kt
 ┃ ┃ ┣ SearchUseCase.kt
 ┃ ┃ ┣ UploadImageUseCase.kt
 ┃ ┃ ┣ ValidateEmailUseCase.kt
 ┃ ┃ ┣ ValidateNoteDescriptionUseCase.kt
 ┃ ┃ ┣ ValidateNoteSubtitleUseCase.kt
 ┃ ┃ ┣ ValidateNoteTitleUseCase.kt
 ┃ ┃ ┣ ValidatePasswordUseCase.kt
 ┃ ┃ ┣ ValidateUserNameUseCase.kt
 ┃ ┃ ┗ ValidateWebLinkUseCase.kt
 ┃ ┗ utilitites
 ┃ ┃ ┗ Exceptions.kt
 ┣ presentation
 ┃ ┣ homeScreen
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ NoteItem.kt
 ┃ ┃ ┃ ┣ SearchField.kt
 ┃ ┃ ┃ ┗ StaggeredVerticalGrid.kt
 ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┣ HomeUiEvent.kt
 ┃ ┃ ┃ ┣ NoteItemUiState.kt
 ┃ ┃ ┃ ┗ NotesUiState.kt
 ┃ ┃ ┣ HomeScreen.kt
 ┃ ┃ ┗ HomeViewModel.kt
 ┃ ┣ loginScreen
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┗ OutlineInputField.kt
 ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┣ LoginUiEvent.kt
 ┃ ┃ ┃ ┗ LoginUiState.kt
 ┃ ┃ ┣ LoginScreen.kt
 ┃ ┃ ┗ LoginViewModel.kt
 ┃ ┣ noteDetailsScreen
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ BottomSheetItem.kt
 ┃ ┃ ┃ ┣ ColorBox.kt
 ┃ ┃ ┃ ┣ NoteInputField.kt
 ┃ ┃ ┃ ┣ ToolBarIconButton.kt
 ┃ ┃ ┃ ┗ UrlDialog.kt
 ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┣ InputFieldUiState.kt
 ┃ ┃ ┃ ┣ LinkUiState.kt
 ┃ ┃ ┃ ┣ NoteDetailsEvent.kt
 ┃ ┃ ┃ ┗ NoteDetailsUiState.kt
 ┃ ┃ ┣ NoteDetailScreen.kt
 ┃ ┃ ┗ NoteDetailsViewModel.kt
 ┃ ┣ registerScreen
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┗ LoadingButton.kt
 ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┣ RegisterUiEvent.kt
 ┃ ┃ ┃ ┗ RegisterUiState.kt
 ┃ ┃ ┣ RegisterScreen.kt
 ┃ ┃ ┗ RegisterViewModel.kt
 ┃ ┣ splashScreen
 ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┗ SplashUiEvent.kt
 ┃ ┃ ┣ SplashScreen.kt
 ┃ ┃ ┗ SplashViewModel.kt
 ┃ ┣ theme
 ┃ ┃ ┣ Color.kt
 ┃ ┃ ┣ Theme.kt
 ┃ ┃ ┗ Type.kt
 ┃ ┗ utility
 ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┗ Screen.kt
 ┣ MainActivity.kt
 ┗ NoteApp.kt
 ```
 
## LICENSE
```MIT License

Copyright (c) 2022 adelayman1

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```
