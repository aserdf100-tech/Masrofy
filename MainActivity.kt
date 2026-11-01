package com.masrofy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private data class Expense(val title: String, val category: String, val amount: Long)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MasrofyApp() }
    }
}

@Composable
fun MasrofyApp() {
    var expenses by remember { mutableStateOf(listOf<Expense>()) }
    var amountText by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    val total = expenses.sumOf { it.amount }
    val currency = NumberFormat.getNumberInstance(Locale("ar", "EG")).format(total) + " ج.م"

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF087F5B),
            secondary = androidx.compose.ui.graphics.Color(0xFFD4A72C),
            background = androidx.compose.ui.graphics.Color(0xFFF6F8F7)
        )
    ) {
        CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl) {
            Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text("مصروفي", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("إدارة مصروفاتك ببساطة", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    item {
                        Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                                Text("إجمالي المصروفات المسجلة", color = androidx.compose.ui.graphics.Color.White)
                                Spacer(Modifier.height(8.dp))
                                Text(currency, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = androidx.compose.ui.graphics.Color.White)
                                Text("الجنيه المصري • بيانات تجريبية محلية", color = androidx.compose.ui.graphics.Color.White.copy(alpha = .8f), fontSize = 12.sp)
                            }
                        }
                    }
                    item {
                        Text("إضافة مصروف", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(value = titleText, onValueChange = { titleText = it }, label = { Text("وصف المصروف") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                        OutlinedTextField(value = amountText, onValueChange = { amountText = it.filter(Char::isDigit) }, label = { Text("المبلغ بالجنيه") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                        Button(
                            onClick = {
                                val amount = amountText.toLongOrNull()
                                if (amount != null && amount > 0 && titleText.isNotBlank()) {
                                    expenses = listOf(Expense(titleText.trim(), "أخرى", amount)) + expenses
                                    amountText = ""
                                    titleText = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("حفظ المصروف") }
                    }
                    item { Text("آخر المصروفات", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) }
                    if (expenses.isEmpty()) {
                        item { Text("لسه مفيش مصروفات. سجّل أول مصروف ليك.") }
                    } else {
                        items(expenses) { expense ->
                            Card(shape = RoundedCornerShape(16.dp)) {
                                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(expense.title, fontWeight = FontWeight.SemiBold)
                                        Text(expense.category, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                                    }
                                    Text(NumberFormat.getNumberInstance(Locale("ar", "EG")).format(expense.amount) + " ج.م", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    item {
                        Text("نسخة أولية للتجربة: البيانات الحالية محفوظة أثناء فتح الشاشة فقط. يلزم استكمال قاعدة البيانات المحلية قبل الاستخدام الفعلي.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
