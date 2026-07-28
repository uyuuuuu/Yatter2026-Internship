1.BindingModel
表示用のデータクラス。 Modelのを加工してある。
bindingmodel/におく

2. converter
bindingmodel/converter/に置く。ModelからBindingModelの変換処理

3. ViewModel
ViewModel()を継承する。
やることは
- uiStateの保持
- データ取得とuiState更新
- onXXイベントの中身の実装

データの保持方法
- MutableStateFlow：あとから変更できる
- StateFlow：変更できない  
uiStateをMutableStateFlowで持つ。  
→backing propertiesという定義

coroutineを起動
viewModelScope.launch：ViewModelが生成・破棄されるタイミングで勝手にコルーチンが生成・破棄される

_uiState.update { it.copy(isLoading = true) }
_uiState.update { old -> old.copy(isLoading = true) }
これは一緒

4. UiState
