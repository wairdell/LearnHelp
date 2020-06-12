package com.wairdell.learnhelp.colormatrix

open interface MatrixColorProvider {

    fun generateMatrixColor(n : Float) : FloatArray

    object BlockWhiteColorProvider : MatrixColorProvider {

        override fun generateMatrixColor(n: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = 0.3086f
            matrixColor[5] = 0.3086f
            matrixColor[10] = 0.3086f

            matrixColor[1] = 0.6094f
            matrixColor[6] = 0.6094f
            matrixColor[11] = 0.6094f

            matrixColor[2] = 0.0820f
            matrixColor[7] = 0.0820f
            matrixColor[12] = 0.0820f

            matrixColor[18] = 1f
            return matrixColor
        }
    }


    object ContrastProvider : MatrixColorProvider {
        override fun generateMatrixColor(n2: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = n2
            matrixColor[6] = n2
            matrixColor[12] = n2

            matrixColor[4] = 128 * (1 - n2)
            matrixColor[9] = 128 * (1 - n2)
            matrixColor[13] = 128 * (1 - n2)

            matrixColor[18] = 1f
            return matrixColor
        }
    }

    object BrightnessProvider : MatrixColorProvider {
        override fun generateMatrixColor(n: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = 1f
            matrixColor[6] = 1f
            matrixColor[12] = 1f

            matrixColor[4] = n
            matrixColor[9] = n
            matrixColor[13] = n

            matrixColor[18] = 1f
            return matrixColor
        }
    }

    object ColorInversionProvider : MatrixColorProvider {
        override fun generateMatrixColor(n: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = -1f
            matrixColor[6] = -1f
            matrixColor[12] = -1f

            matrixColor[4] = 255f
            matrixColor[9] = 255f
            matrixColor[13] = 255f

            matrixColor[18] = 1f
            return matrixColor
        }
    }

    object ThresholdProvider : MatrixColorProvider {
        override fun generateMatrixColor(n: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = 0.3086f * 256
            matrixColor[5] = 0.3086f * 256
            matrixColor[10] = 0.3086f * 256

            matrixColor[1] = 0.6094f * 256
            matrixColor[6] = 0.6094f * 256
            matrixColor[11] = 0.6094f * 256

            matrixColor[2] = 0.0820f * 256
            matrixColor[7] = 0.0820f * 256
            matrixColor[12] = 0.0820f * 256

            matrixColor[4] = -256 * n
            matrixColor[9] = -256 * n
            matrixColor[14] = -256 * n

            matrixColor[18] = 1f
            return matrixColor
        }
    }

    object ColorSaturationProvider : MatrixColorProvider {
        override fun generateMatrixColor(n: Float): FloatArray {
            var matrixColor = FloatArray(20)
            matrixColor[0] = 0.3086f * (1 - n) + n
            matrixColor[5] = 0.3086f * (1 - n)
            matrixColor[10] = 0.3086f * (1 - n)

            matrixColor[1] = 0.6094f * (1 - n)
            matrixColor[6] = 0.6094f * (1 - n) + n
            matrixColor[11] = 0.6094f * (1 - n)

            matrixColor[2] = 0.0820f * (1 - n)
            matrixColor[7] = 0.0820f * (1 - n)
            matrixColor[12] = 0.0820f * (1 - n) + n

            matrixColor[18] = 1f
            return matrixColor
        }
    }


}