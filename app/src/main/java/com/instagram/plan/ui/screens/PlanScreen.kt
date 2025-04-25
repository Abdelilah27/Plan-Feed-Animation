package com.instagram.plan.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.instagram.plan.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

private val InstagramStoryGradientColors = listOf(
    Color(0xFFF58529),
    Color(0xFFFEDA77),
    Color(0xFFDD2A7B),
    Color(0xFF8134AF),
    Color(0xFF515BD4)
)

@Composable
fun PlanScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PlanTopBar(modifier = Modifier)
            PlanMainContent(modifier = Modifier.weight(1f))
            PlanIndicatorSection()
            PlanBottomSection()
        }
    }
}

@Composable
private fun PlanTopBar(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScreenTitle(modifier.weight(1f))
        GiftIcon()
        Spacer(modifier = Modifier.width(12.dp))
        TryPlusButton()
        Spacer(modifier = Modifier.width(12.dp))
        MenuButton()
    }
}

@Composable
private fun ScreenTitle(modifier: Modifier) {
    Text(
        text = "Plan",
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
private fun GiftIcon() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                brush = Brush.horizontalGradient(InstagramStoryGradientColors),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(2.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_card_giftcard_24),
            contentDescription = "Gift",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TryPlusButton() {
    Box(
        modifier = Modifier
            .height(32.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TRY PLUS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun MenuButton() {
    IconButton(
        onClick = { /* Menu Action */ },
        modifier = Modifier.size(32.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.outline_drag_handle_24),
            contentDescription = "Menu"
        )
    }
}

@Composable
private fun PlanMainContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        ProfileSection()
        Spacer(modifier = Modifier.height(24.dp))
        ImageGrid()
    }
}

@Composable
private fun ProfileSection() {
    var shouldAnimate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        shouldAnimate = true
    }

    Column(modifier = Modifier.padding(16.dp)) {
        ProfileHeader(shouldAnimate)
        Spacer(modifier = Modifier.height(24.dp))
        TripleColumnPlaceholders(shouldAnimate)
    }
}

@Composable
private fun ProfileHeader(shouldAnimate: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfilePictureWithBorder(shouldAnimate)
        ProfileTextPlaceholders(
            modifier = Modifier.weight(1f),
            shouldAnimate = shouldAnimate
        )
    }
}

@Composable
private fun ProfilePictureWithBorder(shouldAnimate: Boolean) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = 0.5f,
                        stiffness = 600f
                    )
                )
            }
            alpha.animateTo(1f, animationSpec = tween(150))
        } else {
            scale.snapTo(0f)
            alpha.snapTo(0f)
        }
    }

    Box(
        modifier = Modifier
            .size(60.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            }
            .background(
                brush = Brush.horizontalGradient(InstagramStoryGradientColors),
                shape = CircleShape
            )
            .padding(3.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.image_1),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
private fun ProfileTextPlaceholders(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean
) {
    val widthFractions = remember {
        mutableStateListOf(0f, 0f, 0f, 0f)
    }

    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            listOf(0.4f, 0.8f, 0.8f, 0.3f).forEachIndexed { index, targetWidth ->
                delay(index * 130L)
                widthFractions[index] = targetWidth
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        widthFractions.forEach { widthFraction ->
            PlaceholderBar(widthFraction)
        }
    }
}

@Composable
private fun PlaceholderBar(widthFraction: Float) {
    val animatedWidth by animateFloatAsState(
        targetValue = widthFraction,
        animationSpec = tween(
            durationMillis = 600,
            easing = LinearOutSlowInEasing
        ),
        label = "placeholderWidth"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(animatedWidth)
            .height(10.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
    )
}

@Composable
private fun TripleColumnPlaceholders(shouldAnimate: Boolean) {
    val columnAlphas = remember { mutableStateListOf(0f, 0f, 0f) }
    val columnYOffsets = remember { mutableStateListOf(20f, 20f, 20f) }

    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            listOf(0, 1, 2).forEachIndexed { index, _ ->
                delay(index * 120L)
                launch {
                    columnAlphas[index] = 1f
                    columnYOffsets[index] = 0f
                }
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf(
            Alignment.Start,
            Alignment.CenterHorizontally,
            Alignment.End
        ).forEachIndexed { index, alignment ->
            AnimatedPlaceholderColumn(
                modifier = Modifier.weight(1f),
                alignment = alignment,
                alpha = columnAlphas[index],
                yOffset = columnYOffsets[index],
                shouldAnimate = shouldAnimate && columnAlphas[index] > 0.5f
            )
        }
    }
}

@Composable
private fun AnimatedPlaceholderColumn(
    modifier: Modifier,
    alignment: Alignment.Horizontal,
    alpha: Float,
    yOffset: Float,
    shouldAnimate: Boolean
) {
    Column(
        modifier = modifier
            .wrapContentWidth(alignment)
            .graphicsLayer {
                this.alpha = alpha
                translationY = yOffset
            },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AnimatedPlaceholderBar(0.7f, shouldAnimate)
        AnimatedPlaceholderBar(0.4f, shouldAnimate)
    }
}

@Composable
private fun AnimatedPlaceholderBar(widthFraction: Float, shouldAnimate: Boolean) {
    val animatedWidth by animateFloatAsState(
        targetValue = if (shouldAnimate) widthFraction else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = FastOutSlowInEasing
        ),
        label = "placeholderWidth"
    )

    Box(
        modifier = Modifier
            .widthIn(max = Dp.Infinity)
            .fillMaxWidth(animatedWidth)
            .height(10.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
    )
}

@Composable
private fun ImageGrid() {
    val imageResources = listOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5,
        R.drawable.image_6,
        R.drawable.image_7,
        R.drawable.image_8,
        R.drawable.image_9
    )

    val gridAnimationState = rememberGridAnimationState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val gridWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val gridHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
        val cellSize = maxWidth / 3f
        val cellCenter = cellSize / 2
        val containerCenterX = gridWidthPx / 2
        val containerCenterY = gridHeightPx / (2.5).toFloat()

        if (gridAnimationState.isVisible) {
            imageResources.forEachIndexed { index, res ->
                GridImage(
                    res = res,
                    index = index,
                    gridAnimationState = gridAnimationState,
                    cellSize = cellSize,
                    cellCenter = cellCenter,
                    containerCenterX = containerCenterX,
                    containerCenterY = containerCenterY
                )
            }
        }
    }
}

@Composable
private fun rememberGridAnimationState(): GridAnimationState {
    var isVisible by remember { mutableStateOf(false) }
    var isAssembled by remember { mutableStateOf(false) }
    var shouldSwap by remember { mutableStateOf(false) }
    val transitionProgress = remember { Animatable(0f) }
    val swapProgress = remember { Animatable(0f) }
    val floatPhase = remember { Animatable(0f) }
    val entryAnimationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Floating animation
        launch {
            while (true) {
                floatPhase.animateTo(
                    targetValue = floatPhase.value + 2f * Math.PI.toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(5000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }

        // Animation sequence
        delay(200)
        isVisible = true
        entryAnimationProgress.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = LinearEasing
            )
        )

        delay(2500)
        isAssembled = true
        transitionProgress.animateTo(
            1f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 80f
            )
        )

        delay(1000)
        shouldSwap = true
        swapProgress.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = LinearOutSlowInEasing
            )
        )
    }

    return GridAnimationState(
        isVisible = isVisible,
        isAssembled = isAssembled,
        shouldSwap = shouldSwap,
        transitionProgress = transitionProgress,
        swapProgress = swapProgress,
        floatPhase = floatPhase,
        entryAnimationProgress = entryAnimationProgress
    )
}

private data class GridAnimationState(
    val isVisible: Boolean,
    val isAssembled: Boolean,
    val shouldSwap: Boolean,
    val transitionProgress: Animatable<Float, *>,
    val swapProgress: Animatable<Float, *>,
    val floatPhase: Animatable<Float, *>,
    val entryAnimationProgress: Animatable<Float, *>
)

@Composable
private fun GridImage(
    res: Int,
    index: Int,
    gridAnimationState: GridAnimationState,
    cellSize: Dp,
    cellCenter: Dp,
    containerCenterX: Float,
    containerCenterY: Float
) {
    val goldenRatio = 1.618f
    val goldenAngle = (2 * Math.PI / goldenRatio).toFloat()
    val radius = 0.6f * sqrt(index + 1f)
    val angle = goldenAngle * index

    val goldenSpiralPosition = Offset(
        radius * cos(angle),
        radius * sin(angle)
    )

    val gridPositions = calculateGridPositions(
        index = index,
        cellSize = cellSize,
        containerCenterX = containerCenterX,
        containerCenterY = containerCenterY,
        shouldSwap = gridAnimationState.shouldSwap,
        swapProgress = gridAnimationState.swapProgress.value
    )

    val animatedPosition = calculateAnimatedPosition(
        gridPosition = gridPositions.second,
        spiralPosition = goldenSpiralPosition,
        cellSize = cellSize,
        containerCenterX = containerCenterX,
        containerCenterY = containerCenterY,
        transitionProgress = gridAnimationState.transitionProgress.value
    )

    val floatOffset = if (!gridAnimationState.isAssembled) {
        val phase = gridAnimationState.floatPhase.value + index * 0.5f
        Offset(
            x = sin(phase * 1.5f) * 10f,
            y = cos(phase * 0.8f) * 10f
        )
    } else Offset.Zero

    val entryOffset = calculateEntryOffset(
        entryProgress = gridAnimationState.entryAnimationProgress.value,
        spiralPosition = goldenSpiralPosition,
        cellSize = cellSize,
        gridHeightPx = containerCenterY * 2.5f
    )

    Box(
        modifier = Modifier
            .size(cellSize)
            .offset {
                IntOffset(
                    (animatedPosition.x - cellCenter.toPx() + floatOffset.x + entryOffset.x).roundToInt(),
                    (animatedPosition.y - cellCenter.toPx() + floatOffset.y + entryOffset.y).roundToInt()
                )
            }
            .graphicsLayer {
                if (!gridAnimationState.isAssembled) {
                    rotationX = sin(gridAnimationState.floatPhase.value + index) * 15f
                    rotationY = cos(gridAnimationState.floatPhase.value * 1.3f + index) * 15f
                    rotationZ = sin(gridAnimationState.floatPhase.value * 0.7f + index) * 10f

                    val depthScale =
                        0.9f + 0.1f * (1 + sin(gridAnimationState.floatPhase.value * 0.6f + index)) / 2f
                    scaleX = depthScale
                    scaleY = depthScale

                    val entryScale = 0.5f + 0.5f * gridAnimationState.entryAnimationProgress.value
                    scaleX *= entryScale
                    scaleY *= entryScale
                } else {
                    rotationX = 0f
                    rotationY = 0f
                    rotationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }

                alpha = gridAnimationState.entryAnimationProgress.value
                cameraDistance = 8f
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
    ) {
        Image(
            painter = painterResource(id = res),
            contentDescription = "Grid Image ${index + 1}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        )
    }
}

@Composable
private fun calculateGridPositions(
    index: Int,
    cellSize: Dp,
    containerCenterX: Float,
    containerCenterY: Float,
    shouldSwap: Boolean,
    swapProgress: Float
): Pair<Offset, Offset> {
    val row = index / 3
    val col = index % 3
    val originalPosition = Offset(
        containerCenterX + (col - 1) * cellSize.toPx(),
        (containerCenterY + (row - 0.7) * cellSize.toPx()).toFloat()
    )

    if (!shouldSwap) return originalPosition to originalPosition

    val swappedPositions = mutableListOf<Offset>().apply {
        repeat(9) { idx ->
            val r = idx / 3
            val c = idx % 3
            add(
                Offset(
                    containerCenterX + (c - 1) * cellSize.toPx(),
                    (containerCenterY + (r - 0.7) * cellSize.toPx()).toFloat()
                )
            )
        }
    }.toMutableList()

    val temp = swappedPositions[0]
    swappedPositions[0] = swappedPositions[4]
    swappedPositions[4] = temp

    val swappedPosition = swappedPositions[index]
    val currentPosition = originalPosition + (swappedPosition - originalPosition) * swapProgress

    return originalPosition to currentPosition
}

@Composable
private fun calculateAnimatedPosition(
    gridPosition: Offset,
    spiralPosition: Offset,
    cellSize: Dp,
    containerCenterX: Float,
    containerCenterY: Float,
    transitionProgress: Float
): Offset {
    val startPosition = Offset(
        containerCenterX + spiralPosition.x * cellSize.toPx(),
        containerCenterY + spiralPosition.y * cellSize.toPx()
    )

    return if (transitionProgress == 0f) startPosition
    else startPosition + (gridPosition - startPosition) * transitionProgress
}

@Composable
private fun calculateEntryOffset(
    entryProgress: Float,
    spiralPosition: Offset,
    cellSize: Dp,
    gridHeightPx: Float
): Offset {
    if (entryProgress >= 1f) return Offset.Zero

    val curveFactor = 1 - entryProgress
    val startX = spiralPosition.x * cellSize.toPx() * 1.5f
    val startY = gridHeightPx * 1.2f
    val controlY = gridHeightPx * 0.6f

    val bezierProgress = entryProgress * entryProgress * (3 - 2 * entryProgress)
    val currentY = (1 - bezierProgress) * (1 - bezierProgress) * startY +
            2 * (1 - bezierProgress) * bezierProgress * controlY +
            bezierProgress * bezierProgress * 0f

    return Offset(
        x = startX * curveFactor,
        y = currentY
    )
}

private fun Modifier.instagramGradientBackground(
    shape: RoundedCornerShape = RoundedCornerShape(8.dp)
): Modifier {
    return this.background(
        brush = Brush.horizontalGradient(InstagramStoryGradientColors),
        shape = shape
    )
}

@Composable
private fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@Composable
private fun PlanIndicatorSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Plan and organize your",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Instagram Feed",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        PageIndicator()
    }
}

@Composable
private fun PageIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        DotIndicator(Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        DotIndicator(Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        DotIndicator(Color.Gray)
    }
}

@Composable
private fun DotIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(color, CircleShape)
    )
}

@Composable
private fun PlanBottomSection() {
    Button(
        onClick = { /* Handle connect action */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        InstagramIcon()
        Spacer(modifier = Modifier.width(8.dp))
        ConnectAccountText()
    }
}

@Composable
private fun InstagramIcon() {
    Image(
        painter = painterResource(id = R.drawable.outline_instagram),
        contentDescription = "Instagram",
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun ConnectAccountText() {
    Text(
        text = "CONNECT YOUR ACCOUNT",
        fontWeight = FontWeight.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    MaterialTheme {
        PlanScreen()
    }
}