package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

data class ThemePalette(
  val id: String,
  val name: String,
  val hexDisplay: String,
  val backgroundColor: Color,
  val surfaceColor: Color,
  val primaryColor: Color,
  val onPrimaryColor: Color,
  val containerColor: Color,
  val onContainerColor: Color,
  val textColor: Color,
  val subtextColor: Color,
  val glowColor: Color,
)

val ColorThemes =
  listOf(
    ThemePalette(
      id = "lavender",
      name = "Lavender Minimal",
      hexDisplay = "#FEF7FF",
      backgroundColor = Color(0xFFFEF7FF),
      surfaceColor = Color(0xFFF3EDF7),
      primaryColor = Color(0xFF6750A4),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFEADDFF),
      onContainerColor = Color(0xFF21005D),
      textColor = Color(0xFF6750A4),
      subtextColor = Color(0xFF49454F),
      glowColor = Color(0xFFD0BCFF),
    ),
    ThemePalette(
      id = "mint",
      name = "Sage Mint",
      hexDisplay = "#F0FDF4",
      backgroundColor = Color(0xFFF0FDF4),
      surfaceColor = Color(0xFFDCFCE7),
      primaryColor = Color(0xFF15803D),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFBBF7D0),
      onContainerColor = Color(0xFF14532D),
      textColor = Color(0xFF15803D),
      subtextColor = Color(0xFF166534),
      glowColor = Color(0xFF86EFAC),
    ),
    ThemePalette(
      id = "sunset",
      name = "Peach Sunset",
      hexDisplay = "#FFF7ED",
      backgroundColor = Color(0xFFFFF7ED),
      surfaceColor = Color(0xFFFFEDD5),
      primaryColor = Color(0xFFEA580C),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFFED7AA),
      onContainerColor = Color(0xFF7C2D12),
      textColor = Color(0xFFEA580C),
      subtextColor = Color(0xFF9A3412),
      glowColor = Color(0xFFFDBA74),
    ),
    ThemePalette(
      id = "sky",
      name = "Sky Blue",
      hexDisplay = "#F0F9FF",
      backgroundColor = Color(0xFFF0F9FF),
      surfaceColor = Color(0xFFE0F2FE),
      primaryColor = Color(0xFF0284C7),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFBAE6FD),
      onContainerColor = Color(0xFF075985),
      textColor = Color(0xFF0284C7),
      subtextColor = Color(0xFF0369A1),
      glowColor = Color(0xFF7DD3FC),
    ),
    ThemePalette(
      id = "rose",
      name = "Dusty Rose",
      hexDisplay = "#FFF1F2",
      backgroundColor = Color(0xFFFFF1F2),
      surfaceColor = Color(0xFFFFE4E6),
      primaryColor = Color(0xFFE11D48),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFFECDD3),
      onContainerColor = Color(0xFF881337),
      textColor = Color(0xFFE11D48),
      subtextColor = Color(0xFF9F1239),
      glowColor = Color(0xFFFDA4AF),
    ),
    ThemePalette(
      id = "cream",
      name = "Warm Ivory",
      hexDisplay = "#FAFAF9",
      backgroundColor = Color(0xFFFAFAF9),
      surfaceColor = Color(0xFFF5F5F4),
      primaryColor = Color(0xFF78716C),
      onPrimaryColor = Color(0xFFFFFFFF),
      containerColor = Color(0xFFE7E5E4),
      onContainerColor = Color(0xFF292524),
      textColor = Color(0xFF44403C),
      subtextColor = Color(0xFF78716C),
      glowColor = Color(0xFFD6D3D1),
    ),
    ThemePalette(
      id = "midnight",
      name = "Deep Midnight",
      hexDisplay = "#0F172A",
      backgroundColor = Color(0xFF0F172A),
      surfaceColor = Color(0xFF1E293B),
      primaryColor = Color(0xFF38BDF8),
      onPrimaryColor = Color(0xFF0F172A),
      containerColor = Color(0xFF334155),
      onContainerColor = Color(0xFFF0F9FF),
      textColor = Color(0xFF38BDF8),
      subtextColor = Color(0xFF94A3B8),
      glowColor = Color(0xFF0284C7),
    ),
  )

val PresetWords =
  listOf("hi !!", "hello", "shine", "smile", "breathe", "peace", "focus", "dream")

val ArchiveHistory =
  listOf(
    "hi !!" to "Just a friendly prompt to start your day.",
    "shine" to "Radiate kindness everywhere you go.",
    "smile" to "A curve that sets everything straight.",
    "breathe" to "Take a deep breath and center yourself.",
    "focus" to "Do one thing at a time with intention.",
  )

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          HiScreen(
            modifier =
              Modifier
                .padding(innerPadding)
                .imePadding()
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiScreen(modifier: Modifier = Modifier) {
  var currentWord by remember { mutableStateOf("hi !!") }
  var currentSubtitle by remember { mutableStateOf("Just a friendly prompt to start your day.") }
  var selectedTheme by remember { mutableStateOf(ColorThemes[0]) }
  var showWordDialog by remember { mutableStateOf(false) }
  var showSettingsSheet by remember { mutableStateOf(false) }
  var enableAutoGlow by remember { mutableStateOf(true) }

  val badgeScale = remember { Animatable(1f) }
  val textScale = remember { Animatable(1f) }
  val coroutineScope = rememberCoroutineScope()
  var selectedTab by remember { mutableIntStateOf(0) }

  val animatedBgColor by
    animateColorAsState(targetValue = selectedTheme.backgroundColor, animationSpec = tween(400))
  val animatedPrimaryColor by
    animateColorAsState(targetValue = selectedTheme.primaryColor, animationSpec = tween(400))
  val animatedTextColor by
    animateColorAsState(targetValue = selectedTheme.textColor, animationSpec = tween(400))
  val animatedContainerColor by
    animateColorAsState(targetValue = selectedTheme.containerColor, animationSpec = tween(400))
  val animatedGlowColor by
    animateColorAsState(targetValue = selectedTheme.glowColor, animationSpec = tween(400))
  val animatedSurfaceColor by
    animateColorAsState(targetValue = selectedTheme.surfaceColor, animationSpec = tween(400))

  Box(
    modifier =
      modifier
        .fillMaxSize()
        .background(animatedBgColor)
        .testTag("hi_screen"),
  ) {
    // Ambient background glow
    if (enableAutoGlow) {
      Box(
        modifier =
          Modifier
            .size(320.dp)
            .align(Alignment.Center)
            .blur(80.dp)
            .background(
              Brush.radialGradient(
                colors =
                  listOf(
                    animatedGlowColor.copy(alpha = 0.35f),
                    Color.Transparent,
                  )
              ),
              shape = CircleShape,
            )
      )
    }

    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // Header Section
      Row(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier =
              Modifier
                .width(4.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(animatedPrimaryColor)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text =
              when (selectedTab) {
                0 -> "THE MORNING WORD"
                1 -> "WORD ARCHIVE"
                else -> "COLOR & SETTINGS"
              },
            style =
              MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = selectedTheme.subtextColor,
              ),
            modifier = Modifier.testTag("header_title"),
          )
        }

        // Header Quick Action Buttons
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = { showSettingsSheet = true },
            modifier = Modifier.testTag("settings_button"),
          ) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Color and App Settings",
              tint = animatedPrimaryColor,
            )
          }

          IconButton(
            onClick = {
              val nextThemeIndex = (ColorThemes.indexOf(selectedTheme) + 1) % ColorThemes.size
              selectedTheme = ColorThemes[nextThemeIndex]
            },
            modifier = Modifier.testTag("quick_color_toggle_button"),
          ) {
            Icon(
              imageVector = Icons.Default.Palette,
              contentDescription = "Cycle Background Color",
              tint = animatedPrimaryColor,
            )
          }
        }
      }

      // Main View Tabs Switching
      Box(
        modifier =
          Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
      ) {
        when (selectedTab) {
          0 -> {
            // Tab 0: Daily Word Screen
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center,
              modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            ) {
              // Interactive Sparkle Icon Badge
              Surface(
                modifier =
                  Modifier
                    .size(96.dp)
                    .scale(badgeScale.value)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                      width = 1.5.dp,
                      color = animatedPrimaryColor.copy(alpha = 0.25f),
                      shape = RoundedCornerShape(32.dp),
                    )
                    .shadow(
                      elevation = 4.dp,
                      shape = RoundedCornerShape(32.dp),
                      spotColor = animatedPrimaryColor.copy(alpha = 0.15f),
                    )
                    .clickable(
                      interactionSource = remember { MutableInteractionSource() },
                      indication = ripple(color = animatedPrimaryColor),
                    ) {
                      coroutineScope.launch {
                        val nextThemeIndex = (ColorThemes.indexOf(selectedTheme) + 1) % ColorThemes.size
                        selectedTheme = ColorThemes[nextThemeIndex]
                        badgeScale.animateTo(
                          0.85f,
                          animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh),
                        )
                        badgeScale.animateTo(
                          1f,
                          animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMedium),
                        )
                      }
                    }
                    .testTag("sparkle_badge"),
                color = animatedContainerColor,
                shape = RoundedCornerShape(32.dp),
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Sparkle Icon",
                    tint = selectedTheme.onContainerColor,
                    modifier = Modifier.size(42.dp),
                  )
                }
              }

              Spacer(modifier = Modifier.height(28.dp))

              // Word Display Title
              Box(
                modifier =
                  Modifier
                    .scale(textScale.value)
                    .clickable(
                      interactionSource = remember { MutableInteractionSource() },
                      indication = null,
                      onClick = { showWordDialog = true },
                    )
                    .testTag("hi_card"),
              ) {
                Text(
                  text = currentWord,
                  fontSize =
                    when {
                      currentWord.length <= 4 -> 92.sp
                      currentWord.length <= 7 -> 68.sp
                      else -> 50.sp
                    },
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.SansSerif,
                  letterSpacing = (-2).sp,
                  color = animatedTextColor,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.testTag("hi_text"),
                )
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Minimalist Accent Divider
              Box(
                modifier =
                  Modifier
                    .width(64.dp)
                    .height(1.5.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(animatedPrimaryColor.copy(alpha = 0.4f))
              )

              Spacer(modifier = Modifier.height(16.dp))

              // Subtitle Message
              Text(
                text = currentSubtitle,
                style =
                  MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = selectedTheme.subtextColor.copy(alpha = 0.9f),
                  ),
                textAlign = TextAlign.Center,
                modifier =
                  Modifier
                    .padding(horizontal = 32.dp)
                    .clickable { showWordDialog = true }
                    .testTag("hi_subtitle"),
              )
            }
          }

          1 -> {
            // Tab 1: Archive Screen
            LazyColumn(
              modifier =
                Modifier
                  .fillMaxSize()
                  .padding(horizontal = 24.dp, vertical = 8.dp)
                  .testTag("archive_list"),
              verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              item {
                Text(
                  text = "Previous Words of Inspiration",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = selectedTheme.subtextColor,
                  modifier = Modifier.padding(bottom = 8.dp),
                )
              }
              items(ArchiveHistory) { (word, subtitle) ->
                Card(
                  onClick = {
                    currentWord = word
                    currentSubtitle = subtitle
                    selectedTab = 0
                  },
                  colors =
                    CardDefaults.cardColors(
                      containerColor = animatedSurfaceColor.copy(alpha = 0.7f)
                    ),
                  shape = RoundedCornerShape(16.dp),
                  modifier = Modifier.fillMaxWidth().testTag("archive_item_$word"),
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                  ) {
                    Column(modifier = Modifier.weight(1f)) {
                      Text(
                        text = word,
                        style =
                          MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = selectedTheme.textColor,
                          ),
                      )
                      Spacer(modifier = Modifier.height(4.dp))
                      Text(
                        text = subtitle,
                        style =
                          MaterialTheme.typography.bodySmall.copy(
                            color = selectedTheme.subtextColor,
                          ),
                      )
                    }
                    if (currentWord == word) {
                      Box(
                        modifier =
                          Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(animatedPrimaryColor),
                        contentAlignment = Alignment.Center,
                      ) {
                        Icon(
                          imageVector = Icons.Default.Check,
                          contentDescription = "Active",
                          tint = selectedTheme.onPrimaryColor,
                          modifier = Modifier.size(16.dp),
                        )
                      }
                    }
                  }
                }
              }
            }
          }

          2 -> {
            // Tab 2: Settings & Colors Screen
            SettingsContent(
              selectedTheme = selectedTheme,
              onSelectTheme = { selectedTheme = it },
              enableAutoGlow = enableAutoGlow,
              onToggleGlow = { enableAutoGlow = it },
              currentWord = currentWord,
              onOpenEditWord = { showWordDialog = true },
            )
          }
        }
      }

      // Bottom Section with Action & Navigation
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
      ) {
        // Floating Action Button
        if (selectedTab == 0) {
          FloatingActionButton(
            onClick = { showWordDialog = true },
            modifier =
              Modifier
                .padding(end = 24.dp, bottom = 16.dp)
                .testTag("add_button"),
            shape = RoundedCornerShape(16.dp),
            containerColor = animatedPrimaryColor,
            contentColor = selectedTheme.onPrimaryColor,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
          ) {
            Icon(
              imageVector = Icons.Default.Edit,
              contentDescription = "Change Word",
              modifier = Modifier.size(28.dp),
            )
          }
        }

        // Minimalist Rounded Navigation Bar
        Surface(
          modifier =
            Modifier
              .fillMaxWidth()
              .shadow(elevation = 12.dp, shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
          color = animatedSurfaceColor.copy(alpha = 0.95f),
          shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        ) {
          Row(
            modifier =
              Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            // Tab 1: Daily
            NavigationTabItem(
              icon = Icons.Default.WbSunny,
              label = "Daily",
              isSelected = selectedTab == 0,
              theme = selectedTheme,
              onClick = { selectedTab = 0 },
              tag = "nav_daily",
            )

            // Tab 2: Archive
            NavigationTabItem(
              icon = Icons.Default.BookmarkBorder,
              label = "Archive",
              isSelected = selectedTab == 1,
              theme = selectedTheme,
              onClick = { selectedTab = 1 },
              tag = "nav_archive",
            )

            // Tab 3: Settings
            NavigationTabItem(
              icon = Icons.Default.Settings,
              label = "Settings",
              isSelected = selectedTab == 2,
              theme = selectedTheme,
              onClick = { selectedTab = 2 },
              tag = "nav_settings",
            )
          }
        }
      }
    }
  }

  // Quick Settings Bottom Sheet
  if (showSettingsSheet) {
    ModalBottomSheet(
      onDismissRequest = { showSettingsSheet = false },
      sheetState = rememberModalBottomSheetState(),
      containerColor = selectedTheme.backgroundColor,
    ) {
      SettingsContent(
        selectedTheme = selectedTheme,
        onSelectTheme = { selectedTheme = it },
        enableAutoGlow = enableAutoGlow,
        onToggleGlow = { enableAutoGlow = it },
        currentWord = currentWord,
        onOpenEditWord = {
          showSettingsSheet = false
          showWordDialog = true
        },
      )
    }
  }

  // Change Word Dialog
  if (showWordDialog) {
    var editWordText by remember { mutableStateOf(currentWord) }
    var editSubtitleText by remember { mutableStateOf(currentSubtitle) }

    AlertDialog(
      onDismissRequest = { showWordDialog = false },
      containerColor = selectedTheme.backgroundColor,
      title = {
        Text(
          text = "Change Word & Greeting",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = selectedTheme.primaryColor,
        )
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "Type your word or choose a preset:",
            style = MaterialTheme.typography.bodyMedium,
            color = selectedTheme.subtextColor,
            modifier = Modifier.padding(bottom = 8.dp),
          )

          OutlinedTextField(
            value = editWordText,
            onValueChange = { editWordText = it },
            label = { Text("Word") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("word_input_field"),
            shape = RoundedCornerShape(12.dp),
            colors =
              OutlinedTextFieldDefaults.colors(
                focusedBorderColor = selectedTheme.primaryColor,
                unfocusedBorderColor = selectedTheme.primaryColor.copy(alpha = 0.4f),
              ),
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = editSubtitleText,
            onValueChange = { editSubtitleText = it },
            label = { Text("Subtitle / Prompt") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("subtitle_input_field"),
            shape = RoundedCornerShape(12.dp),
            colors =
              OutlinedTextFieldDefaults.colors(
                focusedBorderColor = selectedTheme.primaryColor,
                unfocusedBorderColor = selectedTheme.primaryColor.copy(alpha = 0.4f),
              ),
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Presets:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = selectedTheme.subtextColor,
            modifier = Modifier.padding(bottom = 4.dp),
          )

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
          ) {
            items(PresetWords) { preset ->
              FilterChip(
                selected = editWordText == preset,
                onClick = { editWordText = preset },
                label = { Text(preset) },
                colors =
                  FilterChipDefaults.filterChipColors(
                    selectedContainerColor = selectedTheme.containerColor,
                    selectedLabelColor = selectedTheme.onContainerColor,
                  ),
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (editWordText.isNotBlank()) {
              currentWord = editWordText.trim()
              currentSubtitle = editSubtitleText.trim()
            }
            showWordDialog = false
          },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = selectedTheme.primaryColor,
              contentColor = selectedTheme.onPrimaryColor,
            ),
          shape = RoundedCornerShape(12.dp),
        ) {
          Text("Save")
        }
      },
      dismissButton = {
        TextButton(onClick = { showWordDialog = false }) {
          Text("Cancel", color = selectedTheme.subtextColor)
        }
      },
    )
  }
}

@Composable
private fun SettingsContent(
  selectedTheme: ThemePalette,
  onSelectTheme: (ThemePalette) -> Unit,
  enableAutoGlow: Boolean,
  onToggleGlow: (Boolean) -> Unit,
  currentWord: String,
  onOpenEditWord: () -> Unit,
) {
  LazyColumn(
    modifier =
      Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 12.dp)
        .testTag("settings_screen_content"),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    item {
      Text(
        text = "Background Color Themes",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = selectedTheme.textColor,
      )
      Text(
        text = "Select your preferred color palette for background & accents",
        style = MaterialTheme.typography.bodySmall,
        color = selectedTheme.subtextColor,
      )
    }

    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorThemes.forEach { theme ->
          val isSelected = selectedTheme.id == theme.id
          Card(
            onClick = { onSelectTheme(theme) },
            colors =
              CardDefaults.cardColors(
                containerColor =
                  if (isSelected) theme.containerColor else theme.surfaceColor.copy(alpha = 0.6f)
              ),
            border =
              if (isSelected) {
                androidx.compose.foundation.BorderStroke(2.dp, theme.primaryColor)
              } else null,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().testTag("theme_card_${theme.id}"),
          ) {
            Row(
              modifier = Modifier.fillMaxWidth().padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier =
                    Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(theme.backgroundColor)
                      .border(1.5.dp, theme.primaryColor, CircleShape),
                  contentAlignment = Alignment.Center,
                ) {
                  Box(
                    modifier =
                      Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(theme.primaryColor)
                  )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                  Text(
                    text = theme.name,
                    style =
                      MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) theme.onContainerColor else theme.textColor,
                      ),
                  )
                  Text(
                    text = theme.hexDisplay,
                    style =
                      MaterialTheme.typography.labelSmall.copy(
                        color = theme.subtextColor,
                      ),
                  )
                }
              }

              if (isSelected) {
                Box(
                  modifier =
                    Modifier
                      .size(24.dp)
                      .clip(CircleShape)
                      .background(theme.primaryColor),
                  contentAlignment = Alignment.Center,
                ) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = theme.onPrimaryColor,
                    modifier = Modifier.size(16.dp),
                  )
                }
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "Word Settings",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = selectedTheme.textColor,
      )
    }

    item {
      Card(
        onClick = onOpenEditWord,
        colors =
          CardDefaults.cardColors(
            containerColor = selectedTheme.surfaceColor.copy(alpha = 0.6f)
          ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().testTag("edit_word_setting_card"),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Column {
            Text(
              text = "Current Word",
              style = MaterialTheme.typography.labelMedium.copy(color = selectedTheme.subtextColor),
            )
            Text(
              text = "\"$currentWord\"",
              style =
                MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = selectedTheme.textColor,
                ),
            )
          }
          Button(
            onClick = onOpenEditWord,
            colors =
              ButtonDefaults.buttonColors(
                containerColor = selectedTheme.primaryColor,
                contentColor = selectedTheme.onPrimaryColor,
              ),
            shape = RoundedCornerShape(12.dp),
          ) {
            Text("Edit Word")
          }
        }
      }
    }

    item {
      Card(
        colors =
          CardDefaults.cardColors(
            containerColor = selectedTheme.surfaceColor.copy(alpha = 0.6f)
          ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Ambient Background Glow",
              style =
                MaterialTheme.typography.bodyLarge.copy(
                  fontWeight = FontWeight.Medium,
                  color = selectedTheme.textColor,
                ),
            )
            Text(
              text = "Soft blurred lighting behind the center word",
              style = MaterialTheme.typography.bodySmall.copy(color = selectedTheme.subtextColor),
            )
          }
          Switch(
            checked = enableAutoGlow,
            onCheckedChange = onToggleGlow,
            colors =
              SwitchDefaults.colors(
                checkedThumbColor = selectedTheme.onPrimaryColor,
                checkedTrackColor = selectedTheme.primaryColor,
              ),
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun NavigationTabItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  theme: ThemePalette,
  onClick: () -> Unit,
  tag: String,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier =
      Modifier
        .clip(RoundedCornerShape(16.dp))
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple(bounded = false, radius = 24.dp),
          onClick = onClick,
        )
        .padding(horizontal = 12.dp, vertical = 4.dp)
        .testTag(tag),
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = if (isSelected) theme.containerColor else Color.Transparent,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint =
          if (isSelected) {
            theme.onContainerColor
          } else {
            theme.textColor.copy(alpha = 0.5f)
          },
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).size(22.dp),
      )
    }
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      style =
        MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
          color =
            if (isSelected) {
              theme.textColor
            } else {
              theme.textColor.copy(alpha = 0.5f)
            },
        ),
    )
  }
}

@Composable
fun Greeting(name: String = "", modifier: Modifier = Modifier) {
  HiScreen(modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun HiPreview() {
  MyApplicationTheme { HiScreen() }
}



