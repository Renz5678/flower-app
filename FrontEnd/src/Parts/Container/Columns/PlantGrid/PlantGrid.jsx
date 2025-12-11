import styles from './PlantGrid.module.css'
import grid from './PlantGrid.png'

export default function PlantGrid() {
    return(
        <>
            <div className={styles.grid}>
                <img src={grid} alt="grid"/>
            </div>
        </>
    );
}